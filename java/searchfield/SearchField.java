package com.ultimate-rad-games;

/**
 * Specialized textField class to sort data model based on text input.
 */
public class SearchField extends JTextField {

    private static final int LIST_HEAD = 0;

    /**
     * Constructor with JList argument, matches sort from top.
     * @param list - JList
     */
    public SearchField(JList<Object> list) {
        this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(1, 1, 1, 1)));

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) { SearchField.this.setText(""); }
        });

        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                filter(list, SearchField.this.getText());
                list.ensureIndexIsVisible(LIST_HEAD);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                filter(list, SearchField.this.getText());
                list.ensureIndexIsVisible(LIST_HEAD);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) { }

            /**
             * Sort JList to input string.
             * @param list - JList
             * @param input - String
             */
            private void filter(JList<Object> list, String input ) {
                DefaultListModel<Object> dataModel = (DefaultListModel<Object>)list.getModel();
                for (int i = 0; i < dataModel.size(); ++i) {
                    Object object = (Object)dataModel.getElementAt(i);
                    if (startsWith(object.toString(), input )) { sortModel(dataModel, i); }
                }
            }
        });
    }

    /**
     * Constructor with int, JList, Color arguments.
     * Matches sort from top, add color highlights.
     * @param width int pane width
     * @param list JList object input
     * @param highlight Color highlight
     */
    public SearchField(int width, JList<Object> list, Color highlight) {
        super(width);
        this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12 ));
        this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(1, 1, 1, 1)));

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) { SearchField.this.setText(""); }
        });

        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                filter(list, SearchField.this.getText());
                list.ensureIndexIsVisible(LIST_HEAD);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                filter(list, SearchField.this.getText());
                list.ensureIndexIsVisible(LIST_HEAD);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) { }

            /**
             * Sort JList to input string.
             * @param list JList object input
             * @param input String input
             */
            private void filter(JList<Object> list, String input ) {
                DefaultListModel<Object> dataModel = (DefaultListModel<Object>)list.getModel();
                for (int i = 0; i <dataModel.size(); ++i) {
                    Object object = (Object)dataModel.getElementAt(i);
                    HighlightLabel header = object.getHeader();  // extends JLabel, add color highlights
                    header.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12 ));
                    if (startsWith(object.toString(), input )) {
                        header.setHighlightColor(highlight, Color.GRAY);
                        header.setHighlightRegion(input);
                        sortModel(dataModel, i);
                    }
                    else { header.setToggle(false); }
                }
            }
        });
    }

    /**
     * Compare string prefix to input.
     * @param str String object input
     * @param prefix String prefix
     * @return boolean result compare
     */
    public boolean startsWith(String str, String prefix) {
        if ( str.isEmpty() || prefix.isEmpty() ) { return false; }

        return startsWithIgnoreCase(str, prefix, true);
    }

    /**
     * Compare string prefix to input; ignore case, true = yes, false = no
     * @param str String object input
     * @param prefix String prefix
     * @param ignoreCase boolean case sensitive
     * @return boolean result compare
     */
    public boolean startsWithIgnoreCase(String str, String prefix, boolean ignoreCase) {
        if ( str == null || prefix == null ) { return ( str == null && prefix == null ); }
        if ( prefix.length() > str.length() ) { return false; }

        return str.regionMatches( ignoreCase, 0, prefix, 0, prefix.length() );
    }

    /**
     * Sort model to current index.
     * @param model DefaultListModel list model object input
     * @param current int index location
     */
    private void sortModel(DefaultListModel<Object> model, int current) {
        for (int i = 0; i < model.getSize(); ++i) { swapObjectAtIndex(model, current, i); }
    }

    /**
     * Swap model object at input indices.
     * @param model DefaultListModel list model object input
     * @param x int index location
     * @param y int index locaton
     */
    public void swapObjectAtIndex(DefaultListModel<Object> model, int x, int y) {
        if ( x < 0 || y < 0 ) { return; }
        if ( x > model.getSize() - 1 || y > model.getSize() - 1 ) { return; }
        Object tmp = model.get(x);
        model.set(x, model.get(y));
        model.set(y, tmp);
    }
}
