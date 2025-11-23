import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TowerFactoryTest {
    @Test
    void weakMediumStrongCreatorsProduceCorrectTypes() {
        //Given
        Tower t1 = new WeakTowerCreator().create(2, 3);
        Tower t2 = new MediumTowerCreator().create(4, 5);
        Tower t3 = new StrongTowerCreator().create(6, 7);

        //Then
        assertInstanceOf(Tower_weak.class, t1);
        assertInstanceOf(Tower_medium.class, t2);
        assertInstanceOf(Tower_strong.class, t3);
    }
}