//working login
// import { useEffect, useState } from "react";

// function StudentDashboard() {

//   const [data, setData] = useState(null);   // ✅ ALWAYS at top

//   const user = JSON.parse(localStorage.getItem("user"));

//   useEffect(() => {
//     if (user) {
//       fetch(`http://localhost:8080/student/api/dashboard/${user.id}`)
//         .then(res => res.json())
//         .then(data => setData(data));
//     }
//   }, []);

//   // ✅ condition AFTER hooks
//   if (!user) {
//     return <h2>Please login first ❌</h2>;
//   }

//   const handleLogout = () => {
//   localStorage.removeItem("user");
//   window.location.href = "/";
// };

//   return (
//     <div>
//       <h1>Welcome {user.name}</h1>

//       {data && (
//         <>
//           <p>Total Applications: {data.totalApplications}</p>
//           <p>Selected: {data.selected}</p>
//           <p>Jobs: {data.jobs}</p>
//         </>
//       )}
//     </div>
//   );
// }

// export default StudentDashboard;

import { useEffect, useState } from "react";
import Navbar from "./Navbar";
import { useNavigate } from "react-router-dom";

function StudentDashboard() {
  const user = JSON.parse(localStorage.getItem("user"));
  const [data, setData] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    if (user) {
      fetch(`http://localhost:8080/student/api/dashboard/${user.id}`)
        .then(res => res.json())
        .then(data => setData(data));
    }
  }, []);

  if (!user) {
    return <h2>Please login first ❌</h2>;
  }

  return (
  <div style={{ background: "#f5f6fa", minHeight: "100vh" }}>

    {/* ✅ ADD THIS */}
    <Navbar />

    <div style={{ padding: "20px" }}>

      {/* 🔶 TOP CARD */}
      <div style={styles.topCard}>
        <h1>Welcome, {user.name.split(" ")[0]} 👋</h1>
        <p>Track your career journey and apply for opportunities easily.</p>

        <div style={{ marginTop: "15px" }}>
          <button style={styles.btnLight} 
           onClick={() => navigate("/opportunities")}>View Opportunities</button>
          <button style={styles.btnDark}
            onClick={() => navigate("/my-applications")}>My Applications</button>
        </div>
      </div>

      {/* 🔷 STATS
      <div style={styles.cardContainer}>
        <div style={styles.card}>
          <h2>{data?.totalApplications || 0}</h2>
          <p>Total Applications</p>
        </div>

        <div style={styles.card}>
          <h2>{data?.selected || 0}</h2>
          <p>Selected</p>
        </div>

        <div style={styles.card}>
          <h2>{data?.jobs || 0}</h2>
          <p>Available Jobs</p>
        </div>
      </div> */}

    </div>
  </div>
);
}

const styles = {
  topCard: {
    background: "#ff6a21",
    color: "white",
    padding: "30px",
    borderRadius: "15px",
    marginBottom: "30px",
  },
  btnLight: {
    padding: "10px 20px",
    marginRight: "10px",
    border: "none",
    borderRadius: "8px",
    background: "white",
    cursor: "pointer"
  },
  btnDark: {
    padding: "10px 20px",
    border: "none",
    borderRadius: "8px",
    background: "#222",
    color: "white",
    cursor: "pointer"
  },
  cardContainer: {
    display: "flex",
    gap: "20px"
  },
  card: {
    flex: 1,
    background: "white",
    padding: "20px",
    borderRadius: "12px",
    textAlign: "center",
    boxShadow: "0 2px 10px rgba(0,0,0,0.1)"
  }
};

export default StudentDashboard;

