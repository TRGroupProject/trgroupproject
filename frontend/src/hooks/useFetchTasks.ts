import { useState, useEffect } from 'react';

type UserPomodoroTask = {
    taskId: string;
    googleUserId: string;
    googleEventId: string;
    title: string;
    description: string;
    calendarStartDateTime: string;
    pomodoroStartDateTime: string | null;
    pomodoroEndDateTime: string | null;
  }

function useFetchTasks(endpoint : string) {
  const [data, setData] = useState<UserPomodoroTask[]>([]);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await fetch(endpoint);
        if (!response.ok) {
          throw new Error(`Error Status: ${response.status}`);
        }

        const data = await response.json();
        setData(data);
      } catch (err) {
        setError(err as Error);
      }
    }

    fetchData();
  }, [endpoint]);

  return { data, error };
}

export default useFetchTasks;
