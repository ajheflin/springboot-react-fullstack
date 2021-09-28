import {Fragment} from "react";
import styles from './Footer.module.css';

const Footer = (props) => {
    return (
        <div className={styles.footer}>
            {props.children}
            <p>&copy; {new Date().getFullYear()} Geek Sources, Inc.</p>
        </div>
    );
}

export default Footer;