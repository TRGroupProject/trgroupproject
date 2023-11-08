import { useGoogleLogin } from "@react-oauth/google";

const Home: React.FC = () => {
  const login = useGoogleLogin({
    onSuccess: (codeResponse) => console.log(codeResponse),
    scope: "https://www.googleapis.com/auth/calendar.readonly",
    onError: (error) => console.error("Login Authentication Failed", error)
  });

  return <>
     <button onClick={() => login()}>
          Log In Using Google
        </button>
  </>;
};

export default Home;
