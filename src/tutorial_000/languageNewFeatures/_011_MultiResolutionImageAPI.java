package tutorial_000.languageNewFeatures;

import java.awt.Image;
import java.awt.image.BaseMultiResolutionImage;
import java.awt.image.MultiResolutionImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class _011_MultiResolutionImageAPI {

	public static void main(String[] args) throws MalformedURLException, IOException {
		/*
		 * Java 9 has introduce a new multi-resolution image API which supports multiple images with different resolution variants. This API allows a set 
		 * of images with different resolution to be used as a single multi-resolution image. Following are major operations of multi-resolution image :
		 * - Image getResolutionVariant(double destImageWidth, double destImageHeight) : Gets a specific image which is best variant to represent this 
		 * 	 logical image at the indicated size.
		 * - List<Image> getResolutionVariants() : Gets a readable list of all resolution variants.
		 * 
		 * Those are available in java.awt.image package (it's mandatory to add java.desktop module to the application).
		 */
		List<URL> imgUrlsList = List.of(_011_MultiResolutionImageAPI.class.getResource("sample_image_3.jpg"),
				_011_MultiResolutionImageAPI.class.getResource("sample_image_2.jpg"),
				_011_MultiResolutionImageAPI.class.getResource("sample_image.jpg"));

		List<Image> images = new ArrayList<Image>();

		for (URL url : imgUrlsList) {
			images.add(ImageIO.read(url));
		}

		// Mix all images into one multiresolution image.
		MultiResolutionImage multiResolutionImage = new BaseMultiResolutionImage(images.toArray(new Image[0]));

		// Get all variants of images (from different resolutions).
		List<Image> variants = multiResolutionImage.getResolutionVariants();

		System.out.println("Total number of images: " + variants.size());

		System.out.println("-------------------------------------");

		for (Image img : variants) {
			// Allow to see each object representing our images.
			System.out.println(img);
		}

		System.out.println("-------------------------------------");

		// Get a resolution-specific image variant for each indicated size. This method, for the BaseMultiResolutionImage class, return the first founded
		// image that resolution is greater or equal to the target resolution.
		Image variant1 = multiResolutionImage.getResolutionVariant(156, 45);
		System.out.printf("Image for destination[%d,%d]: [%d,%d]", 156, 45, variant1.getWidth(null), variant1.getHeight(null));

		Image variant2 = multiResolutionImage.getResolutionVariant(311, 89);
		System.out.printf("\nImage for destination[%d,%d]: [%d,%d]", 311, 89, variant2.getWidth(null), variant2.getHeight(null));

		Image variant3 = multiResolutionImage.getResolutionVariant(622, 178);
		System.out.printf("\nImage for destination[%d,%d]: [%d,%d]", 622, 178, variant3.getWidth(null), variant3.getHeight(null));

		Image variant4 = multiResolutionImage.getResolutionVariant(300, 300);
		System.out.printf("\nImage for destination[%d,%d]: [%d,%d]", 300, 300, variant4.getWidth(null), variant4.getHeight(null));
	}
}
