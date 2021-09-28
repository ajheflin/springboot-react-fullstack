import Header from "./components/org/Header";
import Body from "./components/org/Body";
import Footer from "./components/org/Footer";
import styles from "./App.module.css";

function App() {
    return (
        <div className={styles.app}>
            <Header/>
            <Body/>
            <Footer/>
        </div>
    );
}

export default App;
