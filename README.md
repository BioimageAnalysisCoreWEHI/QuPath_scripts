# QuPath scripts
A set of QuPath scripts for various pre-processing/analysis tasks. Mainly for multiplex analysis. 
More scripts here: [Svidro](https://gist.github.com/Svidro) and [Pete Bankhead](https://gist.github.com/petebankhead) gist.

- add_logMean: add logarithmic value to a measurement
- clean_detections: remove all detections
- gui_phenotype: create phenotypes based on classifier output (GUI)
- multichannel_analysis: Automated multichannel analysis (positive cells > k*mean+std of intensity distribution)
- Otsu_threshold: Computes Otsu threshold 
- ranking_optimised: Rank cell positivity from 0-1 (0 the brightest) 
- stardist: (From Pete Bankhead) Use Stardist for fluorescence cell detection
- stardist_he: (From Pete Bankhead) Use of stardist for H&E cell detection
- stitching_OPAL: Stitching script after Inform processing
- export results: export all measurements as .txt,.csv,.tsv file
- saveMeasurementsMap: saves measurements Map as image
- AddIntensityMeasurements: add cell measurements after detection (mean, std, median, var)
- annotateCellsManual: Force cells to be a defined phenotype (useful for training)
- NearestNeighbours: computes nearest neighbourhood analysis
- DBSCAN: computes DBSCAN clustering
- Stardist_0.3.0: Updated Stardist script for QuPath 0.3.0

# License

These scripts are intended for research use only. It has been made freely available under the terms of the GPLv3 in the hope it is useful for this purpose, and to make analysis methods open and transparent.

# Author
Nina Tubau Ribera
