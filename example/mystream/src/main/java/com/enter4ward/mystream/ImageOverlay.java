package com.enter4ward.mystream;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.imageio.ImageIO;

public class ImageOverlay {

	  
	/**
	 * Gets the server time.
	 *
	 * @return the server time
	 */
	private static final String getServerTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(calendar.getTime());
	}

	public static byte[] editImage(byte[] data, BufferedImage logo) {
		try {
			InputStream in = new ByteArrayInputStream(data);
			BufferedImage img = ImageIO.read(in); // try/catch IOException
			if (img != null) {
				int width = img.getWidth();
				int height = img.getHeight();

				BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = bufferedImage.createGraphics();
				// draw graphics
				g2d.drawImage(img, 0, 0, null);
				// g2d.setBackground(Color.BLACK);
				g2d.setPaint(Color.BLACK);
				g2d.setColor(Color.BLACK);

			
				String text = getServerTime();
                FontMetrics fm = g2d.getFontMetrics();
                Rectangle2D dim = fm.getStringBounds(text, g2d);
				
                Rectangle2D rect = new Rectangle2D.Double(0,0, dim.getWidth(), dim.getHeight());
				g2d.setColor(new Color(0,0,0,.5f));

				g2d.fill(rect);
				g2d.setColor(Color.WHITE);

				g2d.drawString(text, (int)rect.getX(), (int) rect.getMaxY());

				g2d.drawImage(logo, 0, height - logo.getHeight(), null);

				
				
				g2d.dispose();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				ImageIO.write(bufferedImage, "jpg", baos);
				in.close();

				return baos.toByteArray();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
