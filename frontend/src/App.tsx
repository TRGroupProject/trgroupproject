import { BrowserRouter } from 'react-router-dom';
import { Router } from './components/router/Router';
import { GoogleOAuthProvider } from '@react-oauth/google';
import { TasksContext } from './hooks/useContext/taskContext';
import { useEffect, useState } from 'react';

const client_id = import.meta.env.VITE_REACT_APP_CLIENT_ID;
const endpoint = import.meta.env.VITE_FETCH_API_ENDPOINT_DEV;

type UserPomodoroTask = {
  taskId: string;
  googleUserId: string;
  googleEventId: string;
  title: string;
  description: string;
  calendarStartDateTime: string;
  pomodoroStartDateTime: string | null;
  pomodoroEndDateTime: string | null;
};

const App: React.FC = () => {
  useEffect(() => {
    document.title = 'Pomodoro App';
  });

  const [ tasks, setTasks] = useState<UserPomodoroTask[]>([])
  const [type, setType] = useState<string>('all');

  useEffect(() => {
    async function fetchData() {
      try {
        const fetchHeaders = {
          Authorization: '110017430811785439589',
          User: '110017430811785439589',
        };

        const response = await fetch(`${endpoint}?tasks=${type}&google=false`, {
          headers: fetchHeaders,
        });

        if (!response.ok) {
          throw new Error(`Error Status: ${response.status}`);
        }

        const data = await response.json();

        setTasks(data);
      } catch (err) {
        console.log(err);
      }
    }

    fetchData();
  }, [type]);

  const setFilter = (selectedType: string) => {
    console.log("selectedType", selectedType)
    setType(selectedType)
  };

  return (
    <>
      <TasksContext.Provider value={{ tasks: tasks, setFilter }}>
        <GoogleOAuthProvider clientId={client_id}>
          <BrowserRouter>
            <Router />
          </BrowserRouter>
        </GoogleOAuthProvider>
      </TasksContext.Provider>
    </>
  );
};

export default App;