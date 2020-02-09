package interfaces;

import java.util.ArrayList;
import java.util.List;

import enums.LineType;

public interface IASTLine extends IAST {
	
	List<LineType> lineType = new ArrayList<>();

}
