import React, { useState } from 'react';
import axios from 'axios';

const Home = () => {
  const [javaFile, setJavaFile] = useState(null);
  const [repoUrl, setRepoUrl] = useState('');
  const [issues, setIssues] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleFileChange = (e) => {
    setJavaFile(e.target.files[0]);
    setRepoUrl('');
    setIssues([]);
    setError('');
  };

  const handleRepoChange = (e) => {
    setRepoUrl(e.target.value);
    setJavaFile(null);
    setIssues([]);
    setError('');
  };

  const handleSubmit = async () => {
    setLoading(true);
    setIssues([]);
    setError('');

    try {
      if (javaFile) {
        const formData = new FormData();
        formData.append('file', javaFile);

        const res = await axios.post('http://localhost:8080/analyze', formData, {
          headers: { 'Content-Type': 'multipart/form-data' },
        });
        setIssues(res.data.issues || []);
      } else if (repoUrl) {
        const res = await axios.post(
          'http://localhost:8080/analyze/github',
          new URLSearchParams({ repoUrl }),
          { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }
        );
        setIssues(res.data.issues || []);
      } else {
        setError('Please upload a .java file or enter a GitHub repository URL.');
      }
    } catch (err) {
      setError(err.response?.data?.error || 'An error occurred during analysis.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-2xl mx-auto mt-10 p-6 shadow-lg rounded-2xl bg-white space-y-6">
      <h1 className="text-2xl font-bold text-center text-blue-700">Java Code Analyzer</h1>

      <div className="space-y-4">
        <div>
          <label className="block mb-1 font-medium">Upload .java file:</label>
          <input type="file" accept=".java" onChange={handleFileChange} className="w-full" />
        </div>

        <div className="text-center font-semibold text-gray-500">OR</div>

        <div>
          <label className="block mb-1 font-medium">GitHub Repository URL:</label>
          <input
            type="text"
            placeholder="https://github.com/username/repo"
            value={repoUrl}
            onChange={handleRepoChange}
            className="w-full p-2 border border-gray-300 rounded"
          />
        </div>

        <button
          onClick={handleSubmit}
          disabled={loading}
          className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
        >
          {loading ? 'Analyzing...' : 'Analyze'}
        </button>

        {error && <p className="text-red-500 font-medium">{error}</p>}

        {issues.length > 0 && (
          <div className="mt-6">
            <h2 className="text-xl font-semibold mb-2">Analysis Results:</h2>
            <ul className="space-y-2">
              {issues.map((issue, index) => (
                <li key={index} className="p-3 bg-gray-100 rounded shadow-sm">
                  <p><strong>Line:</strong> {issue.line}</p>
                  <p><strong>Rule:</strong> {issue.rule}</p>
                  <p><strong>Message:</strong> {issue.message}</p>
                </li>
              ))}
            </ul>
          </div>
        )}
      </div>
    </div>
  );
};

export default Home;
