import javax.swing.*;
import javax.swing.border.*;

public class About extends JFrame {
	private final int WINDOW_WIDTH = 600, WINDOW_HEIGHT = 450;

	private String license = "<html><body><h2>Engine Editor</h2><pre>"
			+ "Copyright (c) 2022, Patrick Guenthard <me[at]patrickguenthard.ch><br/>" + "<br/>"
			+ "Redistribution and use in source and binary forms, with or without<br/>"
			+ " modification, are permitted provided that the following conditions are met:<br/>" + "<br/>"
			+ "1. Redistributions of source code must retain the above copyright notice, this<br/>"
			+ "   list of conditions and the following disclaimer.<br/>" + ""
			+ "2. Redistributions in binary form must reproduce the above copyright notice,<br/>"
			+ "   this list of conditions and the following disclaimer in the documentation<br/>"
			+ "   and/or other materials provided with the distribution.<br/>" + "<br/>"
			+ "THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\"<br/>"
			+ "AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE<br/>"
			+ "IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE<br/>"
			+ "DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE<br/>"
			+ "FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL<br/>"
			+ "DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR<br/>"
			+ "SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER<br/>"
			+ "CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,<br/>"
			+ "OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE<br/>"
			+ "OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.</pre></body></html>";

	private JLabel infoLabel;

	public About() {
		this.setTitle("About");

//	this.setLayout(new FlowLayout());
		// System.out.println(license);
		this.infoLabel = new JLabel(license);
		this.infoLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.add(this.infoLabel);

		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setVisible(true);
	}
}
