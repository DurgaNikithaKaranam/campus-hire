import { useEffect, useState } from "react";
import Navbar from "./Navbar";
import { useNavigate } from "react-router-dom";

function AdminDashboard() {

  const [stats, setStats] = useState({
    jobs: 0,
    applications: 0,
    students: 0
  });

  const navigate = useNavigate();

  // 🔥 Fetch dashboard stats (you can create API or mock)
  useEffect(() => {
  fetch("http://localhost:8080/admin/api/dashboard")
    .then(res => res.json())
    .then(data => {
      setStats({
        jobs: data.totalJobs,
        applications: data.totalApplications,
        students: data.totalStudents
      });
    });
}, []);

  return (
    <div style={{ background: "#f5f6fa", minHeight: "100vh" }}>

      <Navbar />

      <div style={{ padding: "20px" }}>

        {/* 🔥 HERO SECTION */}
        <div style={styles.hero}>
          <h1>Admin Dashboard 👨‍💼</h1>
          <p>Manage jobs, applications and track platform activity.</p>

          <div style={{ marginTop: "15px" }}>
            <button
              style={styles.addBtn}
              onClick={() => navigate("/add-job")}
            >
              ➕ Add Job
            </button>

            <button
              style={styles.viewBtn}
              onClick={() => navigate("/opportunities")}
            >
              📋 View Jobs
            </button>

            <button
              style={styles.darkBtn}
              onClick={() => navigate("/admin-applications")}
            >
              📄 View Applications
            </button>
          </div>
        </div>

        {/* 🔥 STATS
        <div style={styles.statsContainer}>

          <div style={styles.card}>
            <h1>{stats.jobs}</h1>
            <p>Total Jobs</p>
          </div>

          <div style={styles.card}>
            <h1>{stats.applications}</h1>
            <p>Total Applications</p>
          </div>

          <div style={styles.card}>
            <h1>{stats.students}</h1>
            <p>Total Students</p>
          </div>

        </div> */}

      </div>
    </div>
  );
}

const styles = {

  hero: {
    background: "linear-gradient(135deg, #2c3e50, #4b79a1)",
    color: "white",
    padding: "30px",
    borderRadius: "15px",
    marginBottom: "20px",
    boxShadow: "0 4px 12px rgba(0,0,0,0.2)"
  },

  addBtn: {
    background: "#28a745",
    color: "white",
    border: "none",
    padding: "10px 15px",
    marginRight: "10px",
    borderRadius: "8px",
    cursor: "pointer"
  },

  viewBtn: {
    background: "white",
    color: "#333",
    border: "none",
    padding: "10px 15px",
    marginRight: "10px",
    borderRadius: "8px",
    cursor: "pointer"
  },

  darkBtn: {
    background: "#212529",
    color: "white",
    border: "none",
    padding: "10px 15px",
    borderRadius: "8px",
    cursor: "pointer"
  },

  statsContainer: {
    display: "flex",
    gap: "20px"
  },

  card: {
    flex: 1,
    background: "white",
    padding: "25px",
    borderRadius: "12px",
    textAlign: "center",
    boxShadow: "0 3px 10px rgba(0,0,0,0.1)"
  }

};

export default AdminDashboard;

