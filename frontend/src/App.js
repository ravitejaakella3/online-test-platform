import React from 'react';
import ExamComponent from './components/ExamComponent';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Online Test Platform</h1>
      </header>
      <main>
        <ExamComponent studentId={1} testId={2} />
      </main>
    </div>
  );
}

export default App;
