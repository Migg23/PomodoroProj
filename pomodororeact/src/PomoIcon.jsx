import { useState, useEffect } from 'react';
import './PomoIcon.css';

function PomoIcon() {
    const [timeRemaining, setTimeRemaining] = useState(0);
    const [status, setStatus] = useState('NOT_STARTED');
    const [intervalId, setIntervalId] = useState(null);



    
    const formatTime = (seconds) => {
        const h = String(Math.floor(seconds / 3600)).padStart(2, '0');
        const m = String(Math.floor((seconds % 3600) / 60)).padStart(2, '0');
        const s = String(seconds % 60).padStart(2, '0');
        return `${h}:${m}:${s}`;
    };


    //this accesses the backend java calculation at the start 
    const startSession = async () => {
        await fetch('http://localhost:8080/session/start', { method: 'POST' });
        setStatus('WORKING');


        // the set interval allows the fetchstatus method to happen every second
        const id = setInterval(fetchStatus, 1000);
        setIntervalId(id);
    };


    //acceses the java backend and stops the timer
    const stopSession = async () => {
        clearInterval(intervalId);
        await fetch('http://localhost:8080/session/stop' , {method: 'POST'});

        
        setStatus('STOPPED');
    };


    //coninueously fetches the current timer 
    const fetchStatus = async () => {
        const res = await fetch('http://localhost:8080/session/status');
        const data = await res.json();
        setStatus(data.status);
        setTimeRemaining(data.timeRemaining || 0);
    };


    const resumeSession = async () => {
        const res = await fetch('http://localhost:8080/session/resume');
        const data = await res.json();

        setStatus(data.status);
        setTimeRemaining(data.timeRemaining || 0);

       const temp = setInterval(fetchStatus , 1000);
       setIntervalId(temp);
        
    };

    

    return (
        <div className='OutsideBorder'>
            

            <div className='PomoContainer'>
                <p className='StatusText'>Status: {status}</p>
                <div className='Display'>
                    <p>{formatTime(timeRemaining)}</p>
                </div>
                <div className='ButtonContainer'>
                    <button onClick={startSession}>Start</button>
                    <button onClick={resumeSession}>Resume</button>
                    <button onClick={stopSession}>Stop</button>
                </div>
            </div>
        </div>
        
    );
}

export default PomoIcon;