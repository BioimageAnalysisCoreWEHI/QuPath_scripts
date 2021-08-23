Collection<PathObject> positive_epi = getCellObjects().findAll{(it.getMeasurementList().getMeasurementValue("Ranking Opal 780") < it.getMeasurementList().getMeasurementValue("Ranking Opal 690")) ||
        (it.getMeasurementList().containsNamedMeasurement('Ranking Opal 780') && !it.getMeasurementList().containsNamedMeasurement('Ranking Opal 690'))};

Collection<PathObject> positive_CD3 = getCellObjects().findAll{it.getMeasurementList().getMeasurementValue("Ranking Opal 780") > it.getMeasurementList().getMeasurementValue("Ranking Opal 690") ||
        (it.getMeasurementList().containsNamedMeasurement('Ranking Opal 690') && !it.getMeasurementList().containsNamedMeasurement('Ranking Opal 780'))};

Collection<PathObject> positive_CD8 = positive_CD3.findAll{it.getMeasurementList().getMeasurementValue("Ranking Opal 520") > it.getMeasurementList().getMeasurementValue("Ranking Opal 480") ||
        (it.getMeasurementList().containsNamedMeasurement('Ranking Opal 480') && !it.getMeasurementList().containsNamedMeasurement('Ranking Opal 520'))};

Collection<PathObject> positive_CD4 = positive_CD3.findAll{it.getMeasurementList().getMeasurementValue("Ranking Opal 520") < it.getMeasurementList().getMeasurementValue("Ranking Opal 480") ||
        (it.getMeasurementList().containsNamedMeasurement('Ranking Opal 520') && !it.getMeasurementList().containsNamedMeasurement('Ranking Opal 480'))};

Collection<PathObject> positive_Treg = positive_CD4.findAll{it.getPathClass().toString().contains('570')};

Collection<PathObject> positive_PDL1 = getCellObjects().findAll{it.getPathClass().toString().contains('620') && !it.getPathClass().toString().contains('780')};


resetCellPathClass();

setCellPathClass(positive_epi, 'Epithilial');
setCellPathClass(positive_CD3, 'CD3');
setCellPathClass(positive_CD8, 'CD8');
setCellPathClass(positive_CD4, 'CD4');
setCellPathClass(positive_Treg, 'Treg');
setCellPathClass(positive_PDL1, 'PDL1');




void resetCellPathClass(){
        getCellObjects().forEach(it ->
                    it.setPathClass(null)
                );
    }
   

void setCellPathClass(Collection<PathObject> positive, String phenotypeName) {
        positive.forEach(it -> {
                    PathClass currentClass = it.getPathClass();
                    PathClass pathClass;

                    if (currentClass == null) {
                        pathClass = PathClassFactory.getPathClass(phenotypeName);
                    } else {
                        pathClass = PathClassFactory.getDerivedPathClass(
                                currentClass,
                                phenotypeName,
                                null);
                    }
                    it.setPathClass(pathClass);
                }
        );
    }

void checkCompatibility(){

}