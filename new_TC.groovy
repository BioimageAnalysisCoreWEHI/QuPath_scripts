Collection<PathObject> positive_epi = getCellObjects().findAll{it.getPathClass().toString().contains('780')};

Collection<PathObject> positive_CD3 = getCellObjects().findAll{it.getPathClass().toString().contains('690')};

Collection<PathObject> positive_CD8 = positive_CD3.findAll{it.getPathClass().toString().contains('480')};

Collection<PathObject> positive_CD4 = positive_CD3.findAll{it.getPathClass().toString().contains('520')};

Collection<PathObject> positive_Treg = positive_CD4.findAll{it.getPathClass().toString().contains('570')};

Collection<PathObject> positive_PDL1 = getCellObjects().findAll{it.getPathClass().toString().contains('620') && !it.getPathClass().toString().contains('780')};

resetCellPathClass();

setCellPathClass(positive_epi, 'Epithilial');
setCellPathClass(positive_CD3, 'CD3');
setCellPathClass(positive_CD8, 'CD8');
setCellPathClass(positive_CD4, 'CD4');
setCellPathClass(positive_Treg, 'Treg');
setCellPathClass_2(positive_PDL1, 'PDL1');



void resetCellPathClass(){
        getCellObjects().forEach(it ->
                    it.setPathClass(null)
                );
    }
   

void setCellPathClass(Collection<PathObject> positive, String phenotypeName) {
        
        Hashtable<String, String> map = new Hashtable<>();
        map.put('Epithilial', 'Ranking Opal 780');
        map.put('CD3', 'Ranking Opal 690');
        map.put('CD4', 'Ranking Opal 520');
        map.put('Treg', 'Ranking Opal 570');
        //map.put('Epithilial', 'Ranking Opal 780');

        print(phenotypeName)
        positive.forEach(it -> {

                    PathClass currentClass = it.getPathClass();
                    PathClass pathClass = currentClass;
                    print(currentClass)
                    map_rankings = [:]
                    for(m: map){
                        if(it.getMeasurementList().containsNamedMeasurement(m.getValue())){
                            def ranking_val = it.getMeasurementList().getMeasurementValue(m.getValue())
                            map_rankings.put(m.getValue(),ranking_val)  
                            }   
                                      
                    }

                    if(map_rankings.values().min() == it.getMeasurementList().getMeasurementValue(map[phenotypeName]))
                       pathClass = PathClassFactory.getDerivedPathClass(currentClass,phenotypeName,null);
                    

                    if(map_rankings.find{it.value == map_rankings.values().min()}?.key == 'Ranking Opal 570' && map_rankings.find{it.key == 'Ranking Opal 520'} && map_rankings.size()>1){
                        map_rankings = map_rankings.sort {it.value}
                        val = map_rankings.find{it.value>map_rankings.values().min()}.getKey()
                        pathClass = getPathClass(map.find{it.value == val}.getKey())

                                    }
                   
                    it.setPathClass(pathClass);
                });
    }
    
void setCellPathClass_2(Collection<PathObject> positive, String phenotypeName) {
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



