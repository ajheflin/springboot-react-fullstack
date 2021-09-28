import {useSelector} from "react-redux";
import {Fragment} from "react";
import styles from './Header.module.css';

const Header = (props) => {
    const userData = useSelector(state => state.user);

    console.log(userData);

    return (
        <div className={styles.header}>
            <ul className={styles.nav}>
                {userData.username !== '' &&
                <Fragment>
                    <li>Welcome, {userData.name}</li>
                    <li>Log Out</li>
                </Fragment>
                }
                {userData.username === '' && <li>
                    Log In
                </li>}
                {userData.authorities.includes("ROLE_ADMIN") && <li>
                    View User Listing
                </li>}
            </ul>
        </div>
    );
}

export default Header;