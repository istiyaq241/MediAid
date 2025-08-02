# MediAid
# 🩺 MediAid – JavaFX Emergency Aid System

MediAid is a JavaFX-based desktop application designed to provide emergency treatment guidance in disaster-prone environments where medical access is delayed. It is built with object-oriented principles, JavaFX UI, and Git-based version control.

---

## ✅ Post-Clone Configuration (Stable Setup)

This configuration assumes you just cloned the repository and want to run MediAid successfully on IntelliJ or VS Code. Follow the folder structure and VM config shown below.

---

### 📁 Required Project Structure (after clone)

MediAid/
├── lib/

│ ├──bin/

├── src/
│ └── mediAid/
│ └── Main.java

├── classes/
│ └── production/
│ └── MediAid/
│ └── mediAid/
│ └── Main.class
├── .vscode/
│ └── launch.json
└── README.md

### 🧠 Required VM Options

Use this exact configuration in IntelliJ Run Config:

--module-path lib
--add-modules javafx.controls,javafx.fxml
-classpath classes/production/MediAid
-Dprism.order=sw
-Djava.library.path=lib/bin



---

### 💻 IntelliJ Configuration

- Mark `src/` as Sources Root
- Add all `.jar` files inside `lib/` using Project Structure → Libraries
- Set SDK to JDK 21 (Adoptium / Temurin)
- Ensure `Main.java` declares `package mediAid;`
- Main class in run config: `mediAid.Main`
- In Run Config → "Use classpath of module": choose `MediAid` (not `Ma`)
- Run after Build > Rebuild Project once

---

🐞 Common Errors and Fixes
ClassNotFoundException: mediAid.Main
➤ Set -classpath to: classes/production/MediAid

JavaFX runtime components are missing
➤ Add --module-path lib --add-modules javafx.controls,javafx.fxml

QuantumRenderer: no suitable pipeline found
➤ Use: -Djava.library.path=lib/bin and ensure native DLLs exist






