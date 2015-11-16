package main;

import ui.UI;

public class Main {
  private Main() {}
  public static void main(String[] args) {
    UI ui;
      ui = new ui.TextUI();
    Control control = new Control(ui);
    control.run();
  }
}
