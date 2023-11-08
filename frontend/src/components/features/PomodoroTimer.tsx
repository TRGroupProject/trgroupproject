import { useContext, useEffect, useState } from 'react';
import { useTimer } from 'react-timer-hook';
import Button from './Buttons/Button';
import styled from "@emotion/styled";
import Text from '../features/Text/Text';
import tomato from '../../images/tomato-icon.png';
import { TasksContext } from '../../hooks/useContext/taskcontext';
import { useParams } from 'react-router-dom';

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

function PomodoroTimer() {
  const tasks = useContext(TasksContext);
  const { taskId } = useParams<string>();

  const [isPomodoro, setIsPomodoro] = useState(true);

  const [breakTime] = useState(5); // this is the break time in seconds
  const [pomodoroTime] = useState(10); // this is the pomodoro time in seconds
  const [focusedTime, setFocusedTime] = useState(0);

  const [timerDuration, setTimerDuration] = useState(pomodoroTime);
  const [showRestartButton, setShowRestartButton] = useState(false);
  const [pomodoroStartDateTime, setPomodoroStartDateTime] = useState<Date | null>(null);
  const [pomodoroEndDateTime, setPomodoroEndDateTime] = useState<Date | null>(null);
  const [showResumeButton, setShowRessumeButton] = useState(false);

  const [task, setTask] = useState<UserPomodoroTask>();
  const [taskTitle, setTaskTitle] = useState('');


  const TimerDisplay = styled.div`
  font-size: 100px;
  span {
    margin: 0 0.1em;
  }
`;

  const ButtonWrapper = styled.div`
    margin: 10px;
  `;

  const StyledDiv = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
  `;

  const {
    seconds,
    minutes,
    pause,
    resume,
    totalSeconds,
    restart,
  } = useTimer({ expiryTimestamp: new Date(), onExpire: handleTimerExpire });

  useEffect(() => {

    if (isPomodoro) {
      setTimerDuration(pomodoroTime);
    } else {
      // when timer is showing break time add the pomodoro time (totalSeconds) to the pomodoro focused time
      const totalTime = focusedTime + totalSeconds
      setFocusedTime(totalTime)
      setTimerDuration(breakTime);
    }
      restart(new Date(new Date().getTime() + timerDuration * 1000));
}, [isPomodoro, timerDuration, pomodoroTime, breakTime, restart]);

  useEffect(() => {
    const currentStartDateTime = new Date();
    setPomodoroStartDateTime(currentStartDateTime);
    const paramId = taskId

    const taskData = tasks?.filter((data) => {
      return paramId === data.taskId.toString()
    }
    )

    if (taskData.length > 0) {
      setTask(taskData[0])
      setTaskTitle(taskData[0].title)
    }

    }, [tasks]);

  useEffect(() => {
    if (pomodoroEndDateTime) {
      updateDatabaseWithPomodoroDateTime();
    }
  }, [pomodoroEndDateTime]);

  function handleTimerExpire() {
    // when the time expires set to not pomodoro to start the timer for break
    setIsPomodoro((prevIsPomodoro) => !prevIsPomodoro);
  }

  const updateDatabaseWithPomodoroDateTime = async () => {
    console.log("pomodoroStartDateTime", pomodoroStartDateTime)
    console.log("pomodoroEndDateTime", pomodoroEndDateTime)

    if (pomodoroEndDateTime && pomodoroStartDateTime) {

      const updatedTask = {
        taskId: task?.taskId,
        googleEventId: task?.googleEventId,
        pomodoroStartDateTime: pomodoroStartDateTime.toISOString(),
        pomodoroEndDateTime: pomodoroEndDateTime.toISOString(),
        title: task?.title,
        googleUserId: task?.googleUserId,
        description: task?.description,
        calendarStartDateTime: task?.calendarStartDateTime,
      };

      let id = 0;

      if (taskId !== undefined) {
         id = parseInt(taskId, 10);
      }

      const headers = {
        user: 'VALID_USER_ID',
      };

      console.log("id", id)

      try {
        const url = `http://localhost:8080/api/v1/tests/${id}`;
        const requestOptions: RequestInit = {
          method: 'PATCH',
          headers: {
            'Content-Type': 'application/json',
            ...headers
          },
          body: JSON.stringify(updatedTask),
        };

        const response = await fetch(url, requestOptions);
        console.log("response", response)
        if (!response.ok) {
          throw new Error(`Error Status: ${response.status}`);
        }

        // const updatedData = await response.json();
        // console.log("updatedData", updatedData)
      } catch (err) {
        console.log("err", err)
      }

    }
  }

  const addTotalFocusedTimeToPomodoroEndDateTime = (totalSecondsRemainBeforeTheEndOfPomodoroTime : number) => {
    // calculate the total focus time for pomodoro timer
    const totalFocusedTime = focusedTime + totalSecondsRemainBeforeTheEndOfPomodoroTime
    console.log("totalFocusedTime", totalFocusedTime)
    const newPomodoroEndDateTime = new Date(pomodoroStartDateTime as Date);
    newPomodoroEndDateTime.setSeconds(newPomodoroEndDateTime.getSeconds() + totalFocusedTime);
    setPomodoroEndDateTime(newPomodoroEndDateTime);
  }

  const formatTime = (num: number) => {
    return num.toString().padStart(2, '0');
  }

  const stopTimer = () => {
    pause()
    setShowRestartButton(true);
    let totalSecondsRemainBeforeTheEndOfPomodoroTime = 0;
    if (isPomodoro) {
      // get the the remaining time before the pomodoro time ends
      totalSecondsRemainBeforeTheEndOfPomodoroTime = pomodoroTime - totalSeconds;
    }
    addTotalFocusedTimeToPomodoroEndDateTime(totalSecondsRemainBeforeTheEndOfPomodoroTime);
  }

  const pauseTimer = () => {
    pause()
    setShowRessumeButton(true);
  }

  const resumeTimer = () => {
    resume()
    setShowRessumeButton(false);
  }

  const restartTimer = () => {
    restart(new Date(new Date().getTime() + pomodoroTime * 1000));
    setIsPomodoro(true);
    setShowRestartButton(false);
  }

  return (
    <StyledDiv>
      <div>
        <TimerDisplay>
          <span>{formatTime(minutes)}</span>:<span>{formatTime(seconds)}</span>
        </TimerDisplay>
      </div>
      <div>
        <Text color="" children={taskTitle} type="heading" />
      </div>
      <p>{isPomodoro ?
      <Text color="green" children={"Pomodoro Timer"} type="heading" />
       :
      <Text color="blue" children={"Break Timer"} type="heading" />}
      </p>
      {showRestartButton ?
      <ButtonWrapper>
        <Button handleOnClick={restartTimer} icon={tomato} children="RESTART" />
      </ButtonWrapper>
      :
      <>
        <ButtonWrapper>
          <Button handleOnClick={stopTimer} icon={tomato} children="STOP"  />
        </ButtonWrapper>

        {showResumeButton ?
          <ButtonWrapper>
            <Button handleOnClick={resumeTimer} icon={tomato} children="RESUME" />
          </ButtonWrapper>
          :
          <ButtonWrapper>
            <Button handleOnClick={pauseTimer} icon={tomato} children="PAUSE" />
          </ButtonWrapper>
        }

      </>
    }
    </StyledDiv>
  );
}

export default PomodoroTimer;