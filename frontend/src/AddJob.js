// import { useState } from "react";
// import Navbar from "./Navbar";

// function AddJob() {

//   const [job, setJob] = useState({
//     title: "",
//     companyName: "",
//     type: "",
//     deadline: ""
//   });

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     const formData = new FormData();

//     Object.keys(job).forEach(key => {
//       formData.append(key, job[key]);
//     });

//     await fetch("http://localhost:8080/api/add", {
//       method: "POST",
//       body: formData
//     });

//     alert("Job Added ✅");
//   };

//   return (
//     <div>
//       <Navbar />

//       <form onSubmit={handleSubmit} style={{ padding: "20px" }}>
//         <h2>Add Job</h2>

//         <input placeholder="Title"
//           onChange={e => setJob({...job, title: e.target.value})} />

//         <input placeholder="Company"
//           onChange={e => setJob({...job, companyName: e.target.value})} />

//         <input placeholder="Type"
//           onChange={e => setJob({...job, type: e.target.value})} />

//         <input type="date"
//           onChange={e => setJob({...job, deadline: e.target.value})} />

//         <button>Add Job</button>
//       </form>
//     </div>
//   );
// }

import { useState } from "react";
import Navbar from "./Navbar";

function AddJob() {

  const [job, setJob] = useState({
    title: "",
    companyName: "",
    type: "",
    applyLink: "",
    branches: [],
    years: [],
    deadline: "",
    description: ""
  });

  const handleCheckbox = (field, value) => {
    setJob(prev => ({
      ...prev,
      [field]: prev[field].includes(value)
        ? prev[field].filter(v => v !== value)
        : [...prev[field], value]
    }));
  };

 const handleSubmit = async (e) => {
  e.preventDefault();

  const formData = new FormData();

  formData.append("title", job.title);
  formData.append("companyName", job.companyName);
  formData.append("type", job.type);
  formData.append("applyLink", job.applyLink);
  job.branches.forEach(b => formData.append("branches", b));
  job.years.forEach(y => formData.append("years", y));
  formData.append("deadline", job.deadline);
  formData.append("description", job.description);

  const res = await fetch("http://localhost:8080/admin/add-job", {
    method: "POST",
    body: formData
  });
  const text = await res.text();
  console.log("Response:", text); // 🔥 CHECK THIS

  if (text=="success") {
    alert("Job Added ✅");
    window.location.href = "/opportunities";
  } else {
    alert("Error adding job ❌");
  }
};

  return (
    <form onSubmit={handleSubmit}>
  <Navbar />

  <div style={styles.page}>
    <div style={styles.card}>

      <h2 style={styles.title}>Add New Opportunity</h2>

      {/* GRID INPUTS */}
      <div style={styles.grid}>
        <input
          style={styles.input}
          placeholder="Title"
          value={job.title}
          onChange={e => setJob({...job, title: e.target.value})}
        />

        <input
          style={styles.input}
          placeholder="Company"
          value={job.companyName}
          onChange={e => setJob({...job, companyName: e.target.value})}
        />

        <input
          style={styles.input}
          placeholder="Job Type"
          value={job.type}
          onChange={e => setJob({...job, type: e.target.value})}
        />

        <input
          style={styles.input}
          placeholder="Apply Link"
          value={job.applyLink}
          onChange={e => setJob({...job, applyLink: e.target.value})}
        />
      </div>

      {/* YEARS */}
      <p style={styles.section}>Select Years:</p>
      <div style={styles.checkboxGroup}>
        <label>
          <input type="checkbox"
            onChange={() => handleCheckbox("years", "2")}
          /> 2nd Year
        </label>

        <label>
          <input type="checkbox"
            onChange={() => handleCheckbox("years", "3")}
          /> 3rd Year
        </label>

        <label>
          <input type="checkbox"
            onChange={() => handleCheckbox("years", "4")}
          /> 4th Year
        </label>
      </div>

      {/* BRANCHES */}
      <p style={styles.section}>Select Branches:</p>
      <div style={styles.checkboxGroup}>
        {["CSE","ECE","IT","EEE","MECH","AIML","AIDS"].map(branch => (
          <label key={branch}>
            <input
              type="checkbox"
              onChange={() => handleCheckbox("branches", branch)}
            /> {branch}
          </label>
        ))}
      </div>

      <input
  type="date"
  style={styles.input}
  value={job.deadline}
  onChange={e => setJob({...job, deadline: e.target.value})}
/>

      {/* DESCRIPTION */}
      <textarea
        style={styles.textarea}
        placeholder="Description"
        value={job.description}
        onChange={e => setJob({...job, description: e.target.value})}
      ></textarea>

      {/* BUTTON */}
      <button type="submit" style={styles.button}>
        Add Job
      </button>

    </div>
  </div>
</form>
  );
}

const styles = {
  page: {
    background: "#f4f6f9",
    minHeight: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center"
  },

  card: {
    width: "100%",
    maxWidth: "650px",
    background: "white",
    padding: "30px",
    borderRadius: "16px",
    boxShadow: "0 8px 20px rgba(0,0,0,0.1)"
  },

  title: {
    textAlign: "center",
    marginBottom: "20px",
    fontWeight: "bold"
  },

  grid: {
    display: "grid",
    gridTemplateColumns: "1fr 1fr",
    gap: "15px",
    marginBottom: "15px"
  },

  input: {
    padding: "12px",
    borderRadius: "8px",
    border: "1px solid #ddd",
    outline: "none",
    fontSize: "14px"
  },

  section: {
    marginTop: "15px",
    marginBottom: "10px",
    fontWeight: "500"
  },

  checkboxGroup: {
    display: "flex",
    flexWrap: "wrap",
    gap: "15px",
    marginBottom: "15px"
  },

  textarea: {
    width: "100%",
    padding: "12px",
    borderRadius: "8px",
    border: "1px solid #ddd",
    marginBottom: "15px"
  },

  button: {
    width: "100%",
    padding: "12px",
    background: "linear-gradient(45deg, #28a745, #20c997)",
    color: "white",
    border: "none",
    borderRadius: "8px",
    fontWeight: "bold",
    cursor: "pointer",
    transition: "0.3s"
  }
};
export default AddJob;