# AI Tool used

## ChatGPT (Free: no login version)
- Improve the UI elements
    - Prompt: Convert the current `dialog-box.css` and `main.css` to follow Telegram dark mode style (Midnight), 
      provided `DialogBox.fxml` and `MainWindow.fxml` contents. 
      - Generated a `dialog-box.css` that matches Telegram color scheme but `main.css` was only partially correct 
        (Main background was white)
      - Regenerated `main.css` with code from `MainWindow.java` and it matched Telegram color scheme. Including the 
        other buttons.
      - Did not have to change anything
    - Prompt: Add an icon to the Window bar along with a name, provided the `Main.java` code
      - Generated valid code to add an icon and title name.
      - Modified code to use valid image path.