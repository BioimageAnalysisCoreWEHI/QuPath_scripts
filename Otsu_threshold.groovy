def cells = QPEx.getCellObjects()

def histogram_ = cells.stream()
                .mapToDouble({p -> p.getMeasurementList().getMeasurementValue('CD45RA: Nucleus: Mean')})
                .filter({d -> !Double.isNaN(d)})
                .toArray()

def int Otsu(double [] data ) {
		// Otsu's threshold algorithm
		// M. Emre Celebi 6.15.2007, Fourier Library https://sourceforge.net/projects/fourier-ipal/
		// ported to ImageJ plugin by G.Landini
		
		int ih;
		int threshold=-1;
		int num_pixels=0;
		double total_mean;	/* mean gray-level for the whole image */
		double bcv, term;	/* between-class variance, scaling term */
		double max_bcv;		/* max BCV */
		double [] cnh = new  double [data.length];	/* cumulative normalized histogram */
		double [] mean = new  double [data.length]; /* mean gray-level */
		double [] histo = new  double [data.length];/* normalized histogram */

		/* Calculate total numbre of pixels */
		for ( ih = 0; ih < data.length; ih++ )
			num_pixels=num_pixels+data[ih];
	
		term = 1.0 / ( double ) num_pixels;

		/* Calculate the normalized histogram */
		for ( ih = 0; ih < data.length; ih++ ) {
			histo[ih] = term * data[ih];
		}

		/* Calculate the cumulative normalized histogram */
		cnh[0] = histo[0];
		for ( ih = 1; ih < data.length; ih++ ) {
			cnh[ih] = cnh[ih - 1] + histo[ih];
		}

		mean[0] = 0.0;

		for ( ih = 0 + 1; ih <data.length; ih++ ) {
			mean[ih] = mean[ih - 1] + ih * histo[ih];
		}

		total_mean = mean[data.length-1];

		//	Calculate the BCV at each gray-level and find the threshold that maximizes it 
		threshold = Double.MIN_VALUE;
		max_bcv = 0.0;

		for ( ih = 0; ih < data.length; ih++ ) {
			bcv = total_mean * cnh[ih] - mean[ih];
			bcv *= bcv / ( cnh[ih] * ( 1.0 - cnh[ih] ) );

			if ( max_bcv < bcv ) {
				max_bcv = bcv;
				threshold = ih;
			}
		}
		return threshold;
	}




double[] intArray = new double[histogram_.length];
for (i=0; i<=histogram_.length-1; i++){
  intArray[i] = histogram_[i] ; 
  }

a = Otsu(intArray)

//threshold = autothresh.getThreshold("Otsu", stack_histogram)