import React, { useState } from "react";
import "./S1000D2Html.css";

const S1000D2Html = () => {
  const [file, setFile] = useState(null);
  const [isTransforming, setIsTransforming] = useState(false);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleTransform = async () => {
    if (!file) {
      alert("Please select a file to transform.");
      return;
    }

    setIsTransforming(true);

    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await fetch("http://localhost:8080/api/transform", {
        method: "POST",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`, // Include the token
        },
        body: formData,
      });

      if (response.ok) {
        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = url;
        a.download = "transformed.zip"; // Default download name
        document.body.appendChild(a);
        a.click();
        a.remove();
      } else {
        alert("Failed to transform the file.");
      }
    } catch (error) {
      console.error("Error during transformation:", error);
      alert("An error occurred during the transformation.");
    } finally {
      setIsTransforming(false);
    }
  };

  return (
    <div className="s1000d2html">
      <h2>S1000D to HTML Transformation</h2>
      <div>
        <input type="file" accept=".xml" onChange={handleFileChange} />
        <button onClick={handleTransform} disabled={isTransforming}>
          {isTransforming ? "Transforming..." : "Transform"}
        </button>
      </div>
    </div>
  );
};

export default S1000D2Html;