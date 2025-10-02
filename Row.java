class Row {
    int id;
    String name;
    String email;

    Row(int i, String n, String e) {
        this.id = i;
        this.email = e;
        this.name = n;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + email;
    }
}