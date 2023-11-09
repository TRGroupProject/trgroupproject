import LayoutCard from '../features/Layout/LayoutCard';
import PomodoroTimer from '../features/PomodoroTimer';

const Task: React.FC = () => {
    return (
        <LayoutCard title="Let's focus!">
            <PomodoroTimer />
        </LayoutCard>
    );
};

export default Task;
