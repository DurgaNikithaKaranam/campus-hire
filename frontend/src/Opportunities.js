// import { useEffect, useState } from "react";
// import Navbar from "./Navbar";

// function Opportunities() {

//   const [jobs, setJobs] = useState([]);
//   const [appliedIds, setAppliedIds] = useState([]);

//   const user = JSON.parse(localStorage.getItem("user"));

//   // 🔥 Load jobs
//   useEffect(() => {
//     fetch("http://localhost:8080/api/opportunities")
//       .then(res => res.json())
//       .then(data => setJobs(data));
//   }, []);

//   // 🔥 Load applied jobs
//   useEffect(() => {
//     fetch(`http://localhost:8080/application/myIds?studentId=${user.id}`)
//       .then(res => res.json())
//       .then(data => setAppliedIds(data));
//   }, []);

//   // 🔥 Apply function
//   const handleApply = async (job) => {

//     // open external link
//     if (job.applyLink) {
//       window.open(job.applyLink, "_blank");
//     }

//     const res = await fetch(
//       `http://localhost:8080/application/apply?opportunityId=${job.id}&studentId=${user.id}`
//     );

//     const msg = await res.text();
//     alert(msg);

//     if (!appliedIds.includes(job.id)) {
//   setAppliedIds([...appliedIds, job.id]);
// }
//   };

//   return (
//     <div style={{ background: "#f5f6fa", minHeight: "100vh" }}>

//       <Navbar />

//       <div style={{ padding: "20px" }}>
//         <h2>💼 Available Opportunities</h2>

//         {jobs.map(job => (
//           <div key={job.id} style={styles.card}>

//             <div>
//               <h3>{job.title}</h3>
//               <p>{job.companyName}</p>
//               <span style={styles.badge}>{job.type}</span>
//               <p>
//                 <b>Deadline:</b>{" "}
//                 {job.deadline ? new Date(job.deadline).toLocaleDateString() : "N/A"}
//               </p>
//             </div>

//             <div>
//               {/* ✅ APPLY / APPLIED */}
//               {!appliedIds.includes(job.id) ? (
//                 <button
//                   style={styles.applyBtn}
//                   disabled={appliedIds.includes(job.id)}
//                    onClick={() => handleApply(job)}
//                 >
//                 Apply
//                 </button>
//               ) : (
//                 <span style={styles.applied}>✔ Applied</span>
//               )}
//             </div>

//           </div>
//         ))}
//       </div>
//     </div>
//   );
// }

// const styles = {
//   card: {
//   background: "white",
//   padding: "20px",
//   margin: "15px 0",
//   borderRadius: "12px",
//   display: "flex",
//   justifyContent: "space-between",
//   alignItems: "center",
//   boxShadow: "0 3px 10px rgba(0,0,0,0.1)"
// },
//   badge: {
//     background: "#e3f2fd",
//     padding: "5px 10px",
//     borderRadius: "8px",
//     fontSize: "12px"
//   },
//   applyBtn: {
//     padding: "8px 15px",
//     background: "#0d6efd",
//     color: "white",
//     border: "none",
//     borderRadius: "6px",
//     cursor: "pointer"
//   },
//   applied: {
//   background: "#28a745",
//   color: "white",
//   padding: "6px 12px",
//   borderRadius: "20px",
//   fontWeight: "bold"
// }
// };

// export default Opportunities;

import { useEffect, useState } from "react";
import Navbar from "./Navbar";
import { useNavigate } from "react-router-dom";

function Opportunities() {

  const [jobs, setJobs] = useState([]);
  const [appliedIds, setAppliedIds] = useState([]);

  const user = JSON.parse(localStorage.getItem("user"));
  const navigate = useNavigate();

  // 🔥 Load jobs
  useEffect(() => {
      if (user.role === "student") {
  fetch(`http://localhost:8080/api/student-opportunities/${user.id}`)
    .then(res => res.json())
    .then(data => setJobs(data));
} else {
  fetch("http://localhost:8080/api/opportunities")
    .then(res => res.json())
    .then(data => setJobs(data));
}
  }, []);

  // 🔥 Load applied jobs (only for student)
  useEffect(() => {
    if (user?.role === "student") {
      fetch(`http://localhost:8080/application/myIds?studentId=${user.id}`)
        .then(res => res.json())
        .then(data => setAppliedIds(data));
    }
  }, []);

  // 🔥 APPLY FUNCTION
  const handleApply = async (job) => {

    // open external link
    if (job.applyLink) {
      window.open(job.applyLink, "_blank");
    }

    const res = await fetch(
  `http://localhost:8080/application/apply?opportunityId=${job.id}&studentId=${user.id}`,
  { method: "POST" }
);

    const msg = await res.text();
    alert(msg);

    // prevent duplicates
    if (!appliedIds.includes(job.id)) {
      setAppliedIds([...appliedIds, job.id]);
    }
  };

  // 🔥 DELETE FUNCTION (ADMIN)
  const handleDelete = async (id) => {

    await fetch(`http://localhost:8080/api/delete?id=${id}`, {
  method: "DELETE"
});

    alert("Job deleted ✅");

    // update UI instantly
    setJobs(jobs.filter(job => job.id !== id));
  };

  return (
    <div style={{ background: "#f5f6fa", minHeight: "100vh" }}>

      <Navbar />

      {user?.role === "admin" && (
      <div style={styles.header}>
  <h2 style={styles.heading}>💼 Available Opportunities</h2>

  <button
    style={styles.addBtn}
    onClick={() =>

navigate("/add-job")}
  >
    ➕ Add Job
  </button>
</div>
)}

      <div style={{padding:"20px"}}>

        {jobs.map(job => (
          <div key={job.id} style={styles.card}>

            {/* LEFT */}
            <div>
              <h3>{job.title}</h3>
              <p>{job.companyName}</p>

              <span style={styles.badge}>{job.type}</span>

              <p>
                <b>Deadline:</b>{" "}
                {job.deadline
                  ? new Date(job.deadline).toLocaleDateString()
                  : "N/A"}
              </p>
            </div>

            {/* RIGHT */}
            <div>

              {/* 👨‍🎓 STUDENT */}
              {user?.role === "student" && (
                !appliedIds.includes(job.id) ? (
                  <button
                    style={styles.applyBtn}
                    onClick={() => handleApply(job)}
                  >
                    Apply
                  </button>
                ) : (
                  <span style={styles.applied}>✔ Applied</span>
                )
              )}

              {/* 👨‍💼 ADMIN */}
              {user?.role === "admin" && (
                <button
                  style={styles.deleteBtn}
                  onClick={() => handleDelete(job.id)}
                >
                  Delete
                </button>
              )}

            </div>

          </div>
        ))}

      </div>
    </div>
  );
}

const styles = {
  card: {
  background: "white",
  padding: "20px",
  margin: "15px 0",
  borderRadius: "12px",
  display: "flex",
  justifyContent: "space-between",
  alignItems: "center",
  boxShadow: "0 3px 10px rgba(0,0,0,0.1)"
},

  badge: {
    background: "#e3f2fd",
    padding: "5px 10px",
    borderRadius: "8px",
    fontSize: "12px",
    display: "inline-block",
    marginTop: "5px"
  },

  applyBtn: {
    padding: "8px 15px",
    background: "#0d6efd",
    color: "white",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer"
  },

  applied: {
    background: "#28a745",
    color: "white",
    padding: "6px 12px",
    borderRadius: "20px",
    fontWeight: "bold"
  },

  deleteBtn: {
    padding: "8px 15px",
    background: "#dc3545",
    color: "white",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer"
  },

  // addBtn: {
  //   background: "#28a745",
  //   color: "white",
  //   border: "none",
  //   padding: "10px 15px",
  //   borderRadius: "8px",
  //   marginBottom: "15px",
  //   cursor: "pointer"
  // },

  header: {
  display: "flex",
  justifyContent: "space-between",
  alignItems: "center",
  margin: "20px 30px"
},

heading: {
  fontSize: "22px",
  fontWeight: "bold"
},

addBtn: {
  background: "linear-gradient(45deg, #28a745, #20c997)",
  color: "white",
  border: "none",
  padding: "10px 18px",
  borderRadius: "8px",
  fontWeight: "600",
  cursor: "pointer",
  boxShadow: "0 4px 10px rgba(0,0,0,0.1)",
  transition: "0.3s"
},

addBtnHover: {
  transform: "scale(1.05)"
}

};

export default Opportunities;