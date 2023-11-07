import * as React from 'react';
import { Navigate } from 'react-router-dom';


const LoggedInOnly : React.FC<{children: React.ReactNode}> = ({children}) => {
    const isAuthenticated = true;

    return (
        <>
            {isAuthenticated ? (
                children
            ) : (
                <Navigate to={
                `/`
                } />
            )}
       </>
       )
}

export default LoggedInOnly;
