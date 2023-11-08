import styled from "@emotion/styled";

const ButtonContainer = styled.button`
    display: block;
    align-items: center;
    padding: 0.5em 1em;
    margin: 0;
    border: none;
    border-radius: 0.3em;
    cursor: pointer;
    color: #F3F3F3;
    background-color: #191919;

    &:hover {
        transition: 2ms;
        background-color: #262626;
    }
`;

const Icon = styled.img`
    margin-right: 0.8em;
    max-width: 1em;
`;

interface ButtonProps {
    icon?: string;
    children: string;
    handleOnClick?: () => void;
}

const Button: React.FC<ButtonProps> = ({ icon, children, handleOnClick }) => {
    return(
        <ButtonContainer onClick={handleOnClick}>
            {icon === undefined ? null :
                <Icon src={icon} alt={"Button icon"} />
            }
            <span>{children}</span>
        </ButtonContainer>
    )
}

export default Button;
