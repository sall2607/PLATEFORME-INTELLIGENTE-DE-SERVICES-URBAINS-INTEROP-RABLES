import axios from 'axios';

const GATEWAY_URL = "http://localhost:8090";

// --- 1. REST ---
export const getTransportLines = async () => {
    try {
        const response = await axios.get(`${GATEWAY_URL}/api/v1/mobility/lines`);
        return response.data;
    } catch (error) {
        console.error("Erreur REST:", error);
        throw error;
    }
};

// --- 4. SERVICE gRPC (Version Robuste / Universelle) ---
export const sendAlert = async (type, location) => {
    try {
        // On ne force plus le type, on laisse Axios deviner
        const response = await axios.get(`${GATEWAY_URL}/api/alerts`, {
            params: { type, location }
        });

        console.log("DEBUG gRPC Réponse brute:", response.data); // Regardez la console F12 !

        // CAS 1 : Le backend a renvoyé du JSON { "result": "..." } (Nouveau code)
        if (response.data && response.data.result) {
            return response.data.result;
        }

        // CAS 2 : Le backend a renvoyé du texte brut "..." (Ancien code ou cache)
        if (typeof response.data === 'string') {
            return response.data;
        }

        // CAS 3 : Autre chose (Objet sans clé result)
        return JSON.stringify(response.data);

    } catch (error) {
        console.error("Erreur technique gRPC:", error);
        throw error;
    }
};


// --- 3. GraphQL (Dynamique) ---
export const findRoute = async (start, destination) => {
    // La requête utilise maintenant les variables passées en paramètres
    const query = `
    {
        routes(start: "${start}", destination: "${destination}") {
            duration
            transportMode
        }
    }
    `;
    
    try {
        const response = await axios.post(`${GATEWAY_URL}/graphql`, { query });
        // Vérification si GraphQL a renvoyé des erreurs
        if (response.data.errors) {
            throw new Error(response.data.errors[0].message);
        }
        return response.data.data.routes;
    } catch (error) {
        console.error("Erreur GraphQL:", error);
        throw error;
    }
};

// --- 4. SOAP (XML Strict) ---
export const checkAirQuality = async (zone) => {
    // Correction ici : Assurez-vous que l'espace de noms correspond exactement
    // On met arg0 sans namespace spécifique si le WSDL le demande ainsi
    const xmlBody = 
    `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tns="http://soap.smartcity.com/">
       <soapenv:Header/>
       <soapenv:Body>
          <tns:getAQI>
             <zone>${zone}</zone>
          </tns:getAQI>
       </soapenv:Body>
    </soapenv:Envelope>`;

    try {
        const response = await axios.post(`${GATEWAY_URL}/air`, xmlBody, {
            headers: {
                'Content-Type': 'text/xml; charset=utf-8', // Encodage important
            }
        });
        
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(response.data, "text/xml");
        const returnNode = xmlDoc.getElementsByTagName("return")[0];
        
        if (!returnNode) return "Donnée indisponible";
        return returnNode.textContent;
    } catch (error) {
        console.error("Erreur SOAP:", error);
        throw error;
    }
};

// --- 5. ORCHESTRATEUR (Appel unique) ---
export const planTripViaOrchestrator = async (depart, arrivee) => {
    try {
        const response = await axios.get(`${GATEWAY_URL}/api/orchestrator/plan`, {
            params: { depart, arrivee }
        });
        return response.data;
    } catch (error) {
        console.error("Erreur Orchestrateur:", error);
        throw error;
    }
};