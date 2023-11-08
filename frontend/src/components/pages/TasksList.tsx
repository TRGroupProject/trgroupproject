import styled from "@emotion/styled";
import { NavLink } from "react-router-dom";
import { useContext } from "react";
import { TasksContext } from "../../hooks/useContext/taskcontext";
import LayoutCard from "../features/Layout/LayoutCard";
import Text from "../features/Text/Text";
import DateTimeText from "../features/Text/DateTimeText";
import Button from "../features/Buttons/Button";
import tomato from "../../images/tomato-icon.png";

const StyledText = styled(Text)``;

const TaskList: React.FC = () => {
  const tasks = useContext(TasksContext);

  const TaskContainer = styled.div`
    display: flex;
    flex-direction: column;
    padding: 15px;
    margin: 8px 0;
    border: 1px solid #e4e4e4;
    border-radius: 8px;
    box-shadow: 0px 2px 4px #0000001a;

    &:hover {
      transition: all 0.5s ease-out;
      box-shadow: 0px 2px 4px #a24242;
    }
  `;

  const DescriptionContainer = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 1em;
  `;

  const ButtonContainer = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 1em;
  `;

  console.log("tasks", tasks);

  return (
    <LayoutCard title="Tasks">
      {tasks?.map((task) => (
        <TaskContainer key={task.taskId}>
          <StyledText children={task.title} type="heading" />
          <DescriptionContainer>
          {task.description && (
            <StyledText children={task.description} type="body" />
          )}
          <DateTimeText
            dateTime={new Date(task.calendarStartDateTime)}
            format={"time"}
          />
          <DateTimeText
            dateTime={new Date(task.calendarStartDateTime)}
            format={"date"}
          />
          </DescriptionContainer>
          <ButtonContainer>
            <NavLink to={`/tasks/${task.taskId}`}>
              <Button
                icon={tomato}
                children="START TIMER"
                handleOnClick={() => {}}
              />
            </NavLink>
          </ButtonContainer>
        </TaskContainer>
      ))}
    </LayoutCard>
  );
};

export default TaskList;
