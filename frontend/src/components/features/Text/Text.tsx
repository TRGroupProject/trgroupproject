import styled from "@emotion/styled";

const StyledHeading = styled.h1<TextProps>`
  font-size: ${(props) => (props.size ? props + "px" : "24px")};
  font-weight: bold;
  color: ${(props) => (props.color ? props.color : "#191919")};

  margin: 8px 0;
`;

const StyledText = styled.p<TextProps>`
  font-size: ${(props) => (props.size ? props.size + "px" : "15px")};
  font-weight: normal;
  color: ${(props) => (props.color ? props.color : "#191919")};
  margin: 5px 0;
  display: inline;
`;

interface TextProps {
  children?: string;
  type?: "heading" | "body";
  size?: number;
  color?: string;
}

const Text: React.FC<TextProps> = ({ children, type, color }) => {
  return (
    <>
      {type === "heading" ? (
        <StyledHeading color={color}>{children}</StyledHeading>
      ) : (
        <StyledText color={color}>{children}</StyledText>
      )}
    </>
  );
};

export default Text;
