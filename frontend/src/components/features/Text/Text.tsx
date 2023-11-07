import styled from "@emotion/styled";

const StyledHeading = styled.h1<TextProps>`
  font-size: ${(props) => (props.size ? props.size + "px" : "24px")};
  font-weight: bold;
  color: #191919;
  margin: 8px 0;
`;

const StyledText = styled.p<TextProps>`
  font-size: ${(props) => (props.size ? props.size + "px" : "15px")};
  font-weight: normal;
  color: #191919;
  margin: 5px 0;
`;

interface TextProps {
  children?: string;
  type?: "heading" | "body";
  size?: number;
}

const Text: React.FC<TextProps> = ({ children, type }) => {
  return (
    <>
      {type === "heading" ? (
        <StyledHeading>{children}</StyledHeading>
      ) : (
        <StyledText>{children}</StyledText>
      )}
    </>
  );
};

export default Text;
