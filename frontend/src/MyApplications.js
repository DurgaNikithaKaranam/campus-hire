import { useEffect, useState } from "react";
import Navbar from "./Navbar";

function MyApplications() {

  const [apps, setApps] = useState([]);
  const [jobs, setJobs] = useState([]);

  const user = JSON.parse(localStorage.getItem("user"));

  // 🔥 get applications
  useEffect(() => {
    fetch(`http://localhost:8080/application/api/my/${user.id}`)
      .then(res => res.json())
      .then(data => setApps(data));
  }, []);

  // 🔥 get all jobs (to match details)
  useEffect(() => {
    fetch(`http://localhost:8080/api/opportunities`)
      .then(res => res.json())
      .then(data => setJobs(data));
  }, []);

  // 🔥 combine app + job data
  const getJob = (id) => jobs.find(j => j.id === id);

  return (
    <div style={{ background: "#f5f6fa", minHeight: "100vh" }}>

      <Navbar />

      <div style={{ padding: "20px" }}>
        <h2>📄 My Applications</h2>

        {apps.length === 0 ? (
          <p>No applications yet</p>
        ) : (
          apps.map(app => {
            const job = getJob(app.opportunityId);

            return (
              <div key={app.id} style={styles.card}>

                <div>
                  <h3>{job?.title}</h3>
                  <p>{job?.companyName}</p>

                  <p>
                    <b>Status:</b>{" "}
                    <span style={{
                      color: app.status === "SELECTED" ? "green" : "orange"
                    }}>
                      {app.status}
                    </span>
                  </p>
                </div>

              </div>
            );
          })
        )}
      </div>
    </div>
  );
}

const styles = {
  card: {
    background: "white",
    padding: "20px",
    margin: "10px 0",
    borderRadius: "10px",
    boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
  }
};

export default MyApplications;