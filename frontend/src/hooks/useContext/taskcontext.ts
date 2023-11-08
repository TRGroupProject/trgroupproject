import { createContext } from "react";

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

export const TasksContext = createContext<UserPomodoroTask[]>([]);