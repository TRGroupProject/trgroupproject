import useDateTime from "../../../hooks/useDateTime";
import styled from "@emotion/styled";

const StyledText = styled.p<TextProps>`
  font-size: ${(props) => (props.size ? props.size + "px" : "15px")};
  font-weight: normal;
  color: #191919;
  margin: 5px 0;
`;

interface TextProps {
  size?: number;
} 

interface DateTimeTextProps {
  dateTime: Date;
  format: "date" | "time";
}

const DateTimeText: React.FC<DateTimeTextProps> = ({ dateTime, format }) => {
  const { date, time } = useDateTime(dateTime);

  return (
    <>
      {format === "date" ? (
        <StyledText>{date}</StyledText>
      ) : (
        <StyledText>{time}</StyledText>
      )}
    </>
  );
};

export default DateTimeText;
