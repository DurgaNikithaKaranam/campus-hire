import { useEffect, useState } from "react";
import Navbar from "./Navbar";

function AdminApplications() {

  const [data, setData] = useState(null);
  const [grouped, setGrouped] = useState({});

  useEffect(() => {
    fetch("http://localhost:8080/admin/api/dashboard")
      .then(res => res.json())
      .then(resData => {

        setData(resData);

        const temp = {};

        resData.companyBranchData.forEach(item => {
          const company = item[0];
          const branch = item[1];
          const count = item[2];

          if (!temp[company]) {
            temp[company] = {};
          }

          temp[company][branch] = count;
        });

        setGrouped(temp);
      });
  }, []);

  if (!data) return <h2>Loading...</h2>;

  return (
    <div style={{ background: "#f4f6f9", minHeight: "100vh" }}>

      <Navbar />

      <div style={{ padding: "20px" }}>

        {/* 🔥 CARDS */}
        <div style={styles.cards}>
          <div style={styles.card}>
            <h2>{data.totalApplications}</h2>
            <p>Total Applications</p>
          </div>

          <div style={styles.card}>
            <h2>{data.totalStudents}</h2>
            <p>Total Students</p>
          </div>

          <div style={styles.card}>
            <h2>{data.totalJobs}</h2>
            <p>Total Jobs</p>
          </div>
        </div>

        {/* 🔥 TABLE */}
        <h2>🏢 Company-wise Applications</h2>

        {Object.keys(grouped).map(company => (
          <div key={company} style={styles.companyCard}>

            <h3>{company}</h3>

<table style={{
  width: "100%",
  borderCollapse: "collapse",
  marginTop: "10px"
}}>

  <thead>
    <tr>
      <th style={thStyle}>Branch</th>
      <th style={thStyle}>Applications</th>
    </tr>
  </thead>

  <tbody>
    {Object.entries(grouped[company]).map(([branch, count]) => (
      <tr key={branch}>
        <td style={tdStyle}>{branch}</td>
        <td style={tdStyle}>{count}</td>
      </tr>
    ))}
  </tbody>

</table>

          </div>
        ))}

      </div>
    </div>
  );
}

const styles = {
  cards: {
    display: "flex",
    gap: "20px",
    marginBottom: "30px"
  },

  card: {
    flex: 1,
    background: "white",
    padding: "20px",
    borderRadius: "12px",
    textAlign: "center",
    boxShadow: "0 3px 10px rgba(0,0,0,0.1)"
  },

  companyCard: {
    background: "white",
    padding: "20px",
    borderRadius: "12px",
    marginBottom: "20px",
    boxShadow: "0 3px 10px rgba(0,0,0,0.1)"
  },

 table: {
  width: "100%",
  borderCollapse: "collapse",
  marginTop: "10px"
},

th: {
  textAlign: "left",
  padding: "10px",
  borderBottom: "2px solid #ddd",
  fontWeight: "bold"
},

td: {
  padding: "10px",
  borderBottom: "1px solid #eee"
}
};

const thStyle = {
  textAlign: "left",
  padding: "10px",
  borderBottom: "2px solid #ddd"
};

const tdStyle = {
  padding: "10px",
  borderBottom: "1px solid #eee"
};

export default AdminApplications;