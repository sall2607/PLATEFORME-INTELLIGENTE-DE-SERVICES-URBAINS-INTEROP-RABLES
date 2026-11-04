import React, { useState } from 'react';
import axios from 'axios';
import './App.css'; 
import { planTripViaOrchestrator } from './SmartCityService';

const GATEWAY_URL = "http://localhost:8090";

// --- DICTIONNAIRE DE TRADUCTION ---
const TRANSLATIONS = {
  "METRO": "Métro",
  "BUS": "Bus",
  "TRAIN": "Train / RER",
  "TRAM": "Tramway",
  "ON_TIME": "À l'heure",
  "DELAYED": "Retardé",
  "CANCELLED": "Annulé",
  "ACCIDENT": "Accident",
  "INCENDIE": "Incendie",
  "Metro (Sécurisé - Accident route)": "Métro (Conseillé - Accident)",
  "Tramway (Éco)": "Tramway (Zone polluée)",
  "Vélo / Marche": "Vélo / Marche (Air pur)",
  "Taxi Rapide": "Taxi",
  "Bus Ligne C": "Bus (Ligne C)"
};

function App() {
  // ==========================================
  // 1. ÉTATS (STATE)
  // ==========================================
  const [villeDepart, setVilleDepart] = useState("Gare");
  const [villeArrivee, setVilleArrivee] = useState("Aeroport");
  const [alertType, setAlertType] = useState("ACCIDENT");
  const [alertLocation, setAlertLocation] = useState("Centre-Ville");

  const [transports, setTransports] = useState([]);
  const [routes, setRoutes] = useState([]);
  const [airQuality, setAirQuality] = useState(null);
  const [recommendation, setRecommendation] = useState(""); 
  
  const [isAlertActiveOnTrip, setIsAlertActiveOnTrip] = useState(false); 
  const [isPolluted, setIsPolluted] = useState(false);

  const [statusScan, setStatusScan] = useState("");         
  const [statusSignalement, setStatusSignalement] = useState(""); 

  const [loadingPlan, setLoadingPlan] = useState(false);

  // ==========================================
  // 2. SIGNALEMENT MANUEL (Carte Droite)
  // ==========================================
  const sendAlert = async () => {
    if (!alertLocation) { alert("Indiquez un lieu !"); return; }
    setStatusSignalement("Signalement en cours...");
    try {
        const response = await axios.get(`${GATEWAY_URL}/api/alerts`, {
            params: { type: alertType, location: alertLocation }
        });
        setStatusSignalement("✅ Alerte enregistrée"); 
    } catch (error) {
        setStatusSignalement("❌ Erreur gRPC");
    }
  };

  // ==========================================
  // 3. PLANIFICATEUR (Carte Gauche)
  // ==========================================
  const handlePlanTrip = async () => {
    setLoadingPlan(true);
    setTransports([]); setAirQuality(null); setRecommendation("");
    setStatusScan("Analyse du trafic...");

    try {
      const data = await planTripViaOrchestrator(villeDepart, villeArrivee);
      
      setAirQuality(data.airQuality || "0");
      setTransports(data.transports || []);
      setRecommendation(data.recommendation || "");
      setStatusScan(data.alertMessage || "✅ Trajet fluide");
      setIsAlertActiveOnTrip(data.hasAlert || false);

      // Logique de pollution (Seuil > 50)
      const aqi = parseInt(data.airQuality);
      setIsPolluted(!isNaN(aqi) && aqi > 50);

    } catch (e) {
      setRecommendation("❌ Service indisponible.");
    } finally {
      setLoadingPlan(false);
    }
  };

  // ==========================================
  // 4. ITINÉRAIRE (Carte Milieu)
  // ==========================================
  const findRoute = async () => {
    const query = `
    {
        routes(start: "${villeDepart}", destination: "${villeArrivee}", 
               hasAlert: ${isAlertActiveOnTrip}, isPolluted: ${isPolluted}) {
            transportMode
            duration
        }
    }
    `;
    try {
        const response = await axios.post(`${GATEWAY_URL}/graphql`, { query });
        setRoutes(response.data.data.routes || []);
    } catch(e) { 
        alert("Erreur calcul itinéraire"); 
    }
  };

  return (
    <div className="App" style={{ fontFamily: "'Segoe UI', sans-serif", padding: "20px", backgroundColor: "#f4f7f6", minHeight: "100vh" }}>
      
      {/* HEADER */}
      <div style={{ textAlign: "center", marginBottom: "40px" }}>
        <h1 style={{color: "#2c3e50", fontSize: "2.5em"}}>🏙️ SmartCity Dashboard</h1>
        <div style={{ display: "flex", justifyContent: "center", gap: "15px", marginTop: "20px" }}>
            <div style={{display:'flex', flexDirection:'column', alignItems:'start'}}>
                <label style={{fontSize:'0.75em', fontWeight:'bold', color:'#7f8c8d'}}>DÉPART</label>
                <input style={inputStyle} value={villeDepart} onChange={e => setVilleDepart(e.target.value)} />
            </div>
            <span style={{fontSize:"30px", alignSelf:'center'}}>➔</span>
            <div style={{display:'flex', flexDirection:'column', alignItems:'start'}}>
                <label style={{fontSize:'0.75em', fontWeight:'bold', color:'#7f8c8d'}}>DESTINATION</label>
                <input style={inputStyle} value={villeArrivee} onChange={e => setVilleArrivee(e.target.value)} />
            </div>
        </div>
      </div>

      <div style={{ display: "flex", flexWrap: "wrap", justifyContent: "center", gap: "25px" }}>
        
        {/* --- CARTE 1 : PLANIFICATEUR --- */}
        <div style={cardStyle}>
          <h2 style={{...titleStyle, borderBottom: "3px solid #3498db"}}>🗺️ Transports</h2>
          <button onClick={handlePlanTrip} style={mainBtnStyle} disabled={loadingPlan}>
            {loadingPlan ? "Calcul..." : "Planifier Trajet"}
          </button>

          {statusScan && (
             <div style={{marginTop:"15px", padding:"10px", borderRadius:"6px", fontSize:"0.85em", 
                 backgroundColor: statusScan.includes("⚠️") ? "#fff3cd" : "#d1e7dd",
                 color: statusScan.includes("⚠️") ? "#856404" : "#0f5132", fontWeight:'bold'}}>
                 {statusScan}
             </div>
          )}

          {airQuality && (
            <div style={{ marginTop: "15px", padding:"10px", backgroundColor:"#f8f9fa", borderRadius:"6px" }}>
              <b>Qualité Air :</b> {airQuality} AQI {isPolluted ? "☁️" : "🍃"}
              <p style={{fontSize:'0.8em', margin:'5px 0 0 0', color:'#7f8c8d'}}>{recommendation}</p>
            </div>
          )}

          <div style={{marginTop: "15px", maxHeight: "250px", overflowY: "auto"}}>
            {transports.map((t, i) => (
              <div key={i} style={{borderBottom:"1px solid #eee", padding:"10px 0", display:'flex', justifyContent:'space-between', alignItems:'center'}}>
                <div>
                   <span style={{fontSize:'1.2em', marginRight:'10px'}}>{t.type === 'METRO' ? '🚇' : '🚌'}</span>
                   <b>{t.lineCode}</b> <small>vers {t.destination}</small>
                </div>
                <span style={{fontSize:"0.7em", padding:"3px 7px", borderRadius:"4px", 
                    backgroundColor: t.status === 'ON_TIME' ? '#d1e7dd' : '#f8d7da',
                    color: t.status === 'ON_TIME' ? '#0f5132' : '#842029'}}>
                    {TRANSLATIONS[t.status] || t.status}
                </span>
              </div>
            ))}
          </div>
        </div>

        {/* --- CARTE 2 : ITINÉRAIRE --- */}
        <div style={cardStyle}>
          <h2 style={{...titleStyle, borderBottom: "3px solid #9b59b6"}}>📍 Itinéraire</h2>
          <button onClick={findRoute} style={{...mainBtnStyle, background:"#9b59b6"}}>Calculer Options</button>
          
          {routes.length > 0 && (
            <table style={{width:"100%", marginTop:"20px", fontSize:"0.9em", borderCollapse:"collapse"}}>
                <thead style={{background:"#f3e5f5"}}>
                    <tr><th style={{padding:"10px", textAlign:"left"}}>Moyen</th><th>Durée</th></tr>
                </thead>
                <tbody>
                    {routes.map((r, i) => (
                        <tr key={i} style={{borderBottom:"1px solid #eee"}}>
                            <td style={{padding:"12px 5px"}}>
                                {TRANSLATIONS[r.transportMode] || r.transportMode}
                            </td>
                            <td style={{textAlign:"center", fontWeight:"bold", color: "#9b59b6"}}>
                                {r.duration} min
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
          )}
        </div>

        {/* --- CARTE 3 : URGENCES --- */}
        <div style={cardStyle}>
          <h2 style={{...titleStyle, borderBottom: "3px solid #e74c3c"}}>🚨 Urgences</h2>
          <label style={{fontSize:'0.8em', marginBottom:'5px'}}>Type d'incident</label>
          <select style={{...inputStyle, marginBottom:"15px"}} value={alertType} onChange={e => setAlertType(e.target.value)}>
              <option value="ACCIDENT">🚗 Accident</option>
              <option value="INCENDIE">🔥 Incendie</option>
          </select>
          <label style={{fontSize:'0.8em', marginBottom:'5px'}}>Localisation</label>
          <input style={{...inputStyle, marginBottom:"20px"}} value={alertLocation} onChange={e => setAlertLocation(e.target.value)} />
          <button onClick={sendAlert} style={{...mainBtnStyle, background: "#e74c3c"}}>SIGNALER</button>
          
          {statusSignalement && (
            <div style={{marginTop:"20px", padding:"12px", textAlign:"center", borderRadius:"6px", 
                backgroundColor: statusSignalement.includes("✅") ? "#d1e7dd" : "#f8d7da",
                color: statusSignalement.includes("✅") ? "#0f5132" : "#842029", fontWeight:"bold"}}>
                {statusSignalement}
            </div>
          )}
        </div>

      </div>
    </div>
  );
}

// STYLES
const cardStyle = { background: "white", padding: "25px", borderRadius: "12px", width: "340px", boxShadow: "0 10px 25px rgba(0,0,0,0.05)", display:'flex', flexDirection:'column' };
const titleStyle = { margin: "0 0 20px 0", fontSize: "1.3em", color: "#2c3e50", paddingBottom:"10px" };
const inputStyle = { padding: "12px", borderRadius: "8px", border: "1px solid #dfe6e9", fontSize:"14px", width: "180px", outline: "none" };
const mainBtnStyle = { width: "100%", padding: "14px", background: "#3498db", color: "white", border: "none", borderRadius: "8px", cursor: "pointer", fontSize: "14px", fontWeight:"bold", transition: "0.3s" };

export default App;