import styled from "@emotion/styled";
import LayoutCard from "../../features/Layout/LayoutCard";

const MetricContainer = styled.div`
  margin-bottom: 2em;
  & > * {
    // display: inline;
  }
`;

const MetricName = styled.h4`
  margin-right: 0.5em;
  font-size: 18px;
  text-align: center;
`;

const MetricDataWrapper = styled.div`
  text-align: center;
  & > * {
    display: inline;
  }
`;

const MetricData = styled.div`
  display: inline;
  margin-right: 1em;
  font-size: 18px;

  & > * {
    display: inline;
  }
`;

const MetricUnit = styled.p`
  font-size: 12px;
`;

const Report: React.FC = () => {
  const metricData = [
    {
      id: 0,
      name: "Tasks Completed",
      value: 2
    },
    {
      id: 1,
      name: "Outstanding Tasks",
      value: 5
    },
    {
      id: 2,
      name: "Total Focus Time",
      value: 26,
      unit: "mins"
    }
  ]

  return (
    <LayoutCard title="Report">
      {metricData.map(metric => 
        <MetricContainer key={metric.id}>
        <MetricName>{metric.name}:</MetricName>
        <MetricDataWrapper>
          <MetricData>
            {metric.value} 
            {metric.unit ? <MetricUnit> {metric.unit}</MetricUnit> : null}
          </MetricData>
        </MetricDataWrapper>
      </MetricContainer>
      )}
    </LayoutCard>
  );
};

export default Report;
