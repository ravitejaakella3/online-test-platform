import React, { useEffect, useState, useCallback } from 'react';
import axios from 'axios';

const ExamComponent = ({ studentId, testId }) => {
    const [answers, setAnswers] = useState({});
    const [lastSaveTime, setLastSaveTime] = useState(null);
    const [questions, setQuestions] = useState([]);
    const [nextSaveIn, setNextSaveIn] = useState(30); // Countdown timer
    const [savedAnswers, setSavedAnswers] = useState([]);

    // Add function to fetch saved answers
    const fetchSavedAnswers = useCallback(async () => {
        try {
            const response = await axios.get(
                `http://localhost:8080/api/answers?studentId=${studentId}&testId=${testId}`
            );
            setSavedAnswers(response.data);
            console.log('Fetched saved answers:', response.data);
        } catch (error) {
            console.error('Error fetching saved answers:', error);
        }
    }, [studentId, testId]);

    // Modify autoSave to update saved answers after saving
    const autoSave = useCallback(async () => {
        if (Object.keys(answers).length === 0) return;

        try {
            const answersArray = Object.entries(answers).map(([questionId, response]) => ({
                questionId: parseInt(questionId),
                response,
                status: 'ATTEMPTED'
            }));

            const response = await axios.post('http://localhost:8080/api/autosave', {
                studentId,
                testId,
                answers: answersArray
            });

            setLastSaveTime(new Date().toLocaleTimeString());
            setNextSaveIn(30);
            
            // Update saved answers
            setSavedAnswers(response.data);
            console.log('Auto-save successful:', response.data);
        } catch (error) {
            console.error('Auto-save failed:', error);
        }
    }, [answers, studentId, testId]);

    // Countdown timer effect
    useEffect(() => {
        const countdownId = setInterval(() => {
            setNextSaveIn(prev => (prev > 0 ? prev - 1 : 30));
        }, 1000);

        return () => clearInterval(countdownId);
    }, []);

    // Auto-save interval
    useEffect(() => {
        const intervalId = setInterval(autoSave, 30000);
        return () => clearInterval(intervalId);
    }, [autoSave]);

    // Fetch questions on component mount
    useEffect(() => {
        axios.get(`http://localhost:8080/api/questions/test/${testId}`)
            .then(response => setQuestions(response.data))
            .catch(error => console.error('Error fetching questions:', error));
    }, [testId]);

    // Fetch saved answers on component mount
    useEffect(() => {
        fetchSavedAnswers();
    }, [fetchSavedAnswers]);

    const handleAnswerChange = (questionId, response) => {
        setAnswers(prev => ({
            ...prev,
            [questionId]: response
        }));
    };

    return (
        <div className="exam-container">
            <div className="status-bar">
                <h2>Science Quiz</h2>
                <div className="save-info">
                    {lastSaveTime && (
                        <div className="save-status">Last saved at: {lastSaveTime}</div>
                    )}
                    <div className="next-save">
                        Next auto-save in: {nextSaveIn} seconds
                    </div>
                </div>
            </div>
            
            <div className="questions-list">
                {questions.map(question => (
                    <div key={question.id} className="question-item">
                        <p>{question.text}</p>
                        <textarea
                            value={answers[question.id] || ''}
                            onChange={(e) => handleAnswerChange(question.id, e.target.value)}
                            placeholder="Enter your answer"
                        />
                    </div>
                ))}
            </div>

            <div className="debug-info">
                <h3>Debug Information</h3>
                <p>Student ID: {studentId}</p>
                <p>Test ID: {testId}</p>
                <p>Last Save: {lastSaveTime}</p>
                <p>Next Save: {nextSaveIn}s</p>
                <p>Saved Answers: {savedAnswers.length}</p>
                <pre>{JSON.stringify(savedAnswers, null, 2)}</pre>
            </div>

            <style jsx>{`
                .status-bar {
                    position: sticky;
                    top: 0;
                    background: white;
                    padding: 10px;
                    border-bottom: 1px solid #eee;
                    z-index: 100;
                }
                .save-info {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    background: #f5f5f5;
                    padding: 8px;
                    border-radius: 4px;
                    margin: 10px 0;
                }
                .save-status {
                    color: green;
                }
                .next-save {
                    color: #666;
                }
                .debug-info {
                    margin-top: 20px;
                    background: #f9f9f9;
                    padding: 10px;
                    border: 1px solid #ddd;
                    border-radius: 4px;
                }
                .debug-info pre {
                    background: #eee;
                    padding: 10px;
                    border-radius: 4px;
                    overflow-x: auto;
                }
            `}</style>
        </div>
    );
};

export default ExamComponent;