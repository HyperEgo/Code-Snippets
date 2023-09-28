public enum Payload {
	BALLISTIC(5),
	BIO_CHEMICAL(50),
	NUCLEAR(500);

	int radius;	

	Payload(int rad) {
		this.radius = rad;
	}

	public int getRadius() {
		final int BLAST_RADIUS = this.radius;
		return BLAST_RADIUS;
	}
}
