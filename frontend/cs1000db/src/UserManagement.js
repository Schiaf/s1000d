import React, { useState, useEffect } from "react";
import "./UserManagement.css"; // Optional: Add styles for this page

const UserManagement = () => {
  const [users, setUsers] = useState([]);
  const [formData, setFormData] = useState({ id: null, login: "", password: "" });
  const [isEditing, setIsEditing] = useState(false);

  // Fetch users from the backend
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/users", {
          method: "GET",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        });

        if (response.ok) {
          const data = await response.json();
          setUsers(data);
          resetForm();
        } else {
          console.error("Failed to fetch users");
        }
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    };

    fetchUsers();
  }, []);

  // Handle form input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const resetForm = () => {
    setFormData({ id: null, login: "", password: "" });
    setIsEditing(false);
  };

  // Handle form submission for create or edit
  const handleSubmit = async (e) => {
    e.preventDefault();

    const method = isEditing ? "PUT" : "POST";
    const url = isEditing
      ? `http://localhost:8080/api/users/${formData.id}`
      : "http://localhost:8080/api/users";

    try {
      const response = await fetch(url, {
        method,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        const updatedUser = await response.json();
        if (isEditing) {
          setUsers(users.map((user) => (user.id === updatedUser.id ? updatedUser : user)));
        } else {
          setUsers([...users, updatedUser]);
        }
        setFormData({ id: null, login: "", password: "" });
        setIsEditing(false);
      } else {
        console.error("Failed to save user");
      }
    } catch (error) {
      console.error("Error saving user:", error);
    }
  };

  // Handle delete user
  const handleDelete = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/users/${id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });

      if (response.ok) {
        setUsers(users.filter((user) => user.id !== id));
      } else {
        console.error("Failed to delete user");
      }
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };

  // Handle edit user
  const handleEdit = (user) => {
    setFormData({
      id: user.id,
      login: user.login,
      password: "", // Leave the password field empty
    });
    setIsEditing(true);
  };

  return (
    <div className="user-management">
      <h2>User Management</h2>

      <form onSubmit={handleSubmit} className="user-form">
        <input
          type="text"
          name="login"
          placeholder="login"
          value={formData.login}
          onChange={handleInputChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={formData.password} 
          onChange={handleInputChange}
          required
        />
        <button type="submit">{isEditing ? "Update User" : "Create User"}</button>
      </form>

      <table className="user-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Login</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
        {users
          .filter((user) => user.login !== "admin") // Exclude the admin user
          .map((user) => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.login}</td>
              <td>
                <button onClick={() => handleEdit(user)}>Edit</button>
                <button onClick={() => handleDelete(user.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UserManagement;