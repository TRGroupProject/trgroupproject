import { BrowserRouter } from "react-router-dom";
import {Router} from "./components/router/Router";
import { GoogleOAuthProvider } from '@react-oauth/google';

const client_id = import.meta.env.VITE_REACT_APP_CLIENT_ID;

const App: React.FC = () => {

  return (
    <>
     <GoogleOAuthProvider clientId={client_id}>
        <BrowserRouter>
          <Router />
        </BrowserRouter>
      </GoogleOAuthProvider>
    </>
  )
}

export default App;