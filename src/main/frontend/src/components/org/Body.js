import {useDispatch, useSelector} from "react-redux";
import {useState} from "react";
import axios from "axios";
import {userActions} from "../../store/userSlice";
import styles from './Body.module.css';

const Body = (props) => {
    const [invalid, setInvalid] = useState(false);

    const username = useSelector(state => state.user.username);

    const dispatch = useDispatch();

    const [enteredUsername, setEnteredUsername] = useState('');
    const [enteredPassword, setEnteredPassword] = useState('');

    const usernameChangeHandler = (event) => {
        setEnteredUsername(event.target.value);
    }
    const passwordChangeHandler = (event) => {
        setEnteredPassword(event.target.value);
    }

    const formSubmitHandler = (event) => {
        event.preventDefault();

        axios.post('http://localhost:8080/authenticate', {
            username: enteredUsername,
            password: enteredPassword
        })
            .then(response => {
                if (response.data.jwt !== null) {
                    dispatch(userActions.logIn({
                        username: response.data.username,
                        name: response.data.name,
                        jwt: response.data.jwt,
                        authorities: response.data.authorities,
                        id: response.data.id
                    }));
                }
            })
            .catch(error => {
                setInvalid(true);
            });
    }

    return (
        <div className={styles.body}>
            {username === '' && <form className={styles.login} onSubmit={formSubmitHandler}>
                <input placeholder="Username" type='text' name='username' value={enteredUsername} onChange={usernameChangeHandler}/>
                <input placeholder="Password" type='password' name='password' value={enteredPassword} onChange={passwordChangeHandler}/>
                {invalid && <p className={styles.invalid}>The username/password entered was incorrect.</p>}
                <input type='submit' name='Submit' value='Log In'/>
            </form>}
        </div>
    );
}

export default Body;