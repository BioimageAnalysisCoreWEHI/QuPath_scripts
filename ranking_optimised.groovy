MeasurementsToAdd = ['Opal 520: Cell: Mean','Opal 570: Cell: Mean', 'Opal 480: Cell: Mean', 'Opal 690: Cell: Mean', 'Opal 780: Cell: Mean', 'Opal 620: Cell: Mean']
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import groovy.time.TimeCategory 
import groovy.time.TimeDuration

Date start = new Date();

cells = getCellObjects();

ExecutorService pool=Executors.newFixedThreadPool(6)
MeasurementsToAdd.each{m->
    pool.execute{
        ReadAssignMeasurement( m, cells);

    }
}

//println "done cycle"
pool.shutdown()                 //all tasks submitted
while (!pool.isTerminated()){}  //waitfor termination
//println 'Finished all threads'

def ReadAssignMeasurement(m, cells){
    Map<Integer, Float> A = new HashMap<>();
    def sorted_array = [:];
    ArrayList<PathClass> positive = cells.findAll{it.getPathClass().toString().contains(m.split(':')[0])}
    
    Integer j = 0;
   
    positive.each{
        
        Float value = (Float) it.getMeasurementList().getMeasurementValue(m);
        A.put(j, value);
        j++;

    }

    sorted_array = A.sort{-it.value};    
    sorted_array.eachWithIndex { key, val, i ->  
        sorted_array[key] = i}
        //index ++}
        
    Integer cpt=0;
    positive.each{
        def ml = it.getMeasurementList()
        Float ranking  = (Float) sorted_array.find{ it.key == cpt }.value
        ml.putMeasurement('Ranking '+ m.split(':')[0], ranking/positive.size())
        ml.close()
        cpt++;
    }
    fireHierarchyUpdate();
    print("done " + m);

}

Date stop = new Date()

TimeDuration td = TimeCategory.minus( stop, start )
println td
