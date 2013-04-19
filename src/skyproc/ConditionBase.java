/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;
import lev.LImport;
import lev.LFlags;
import lev.Ln;
import skyproc.Condition.Operator;
import skyproc.exceptions.BadParameter;
import skyproc.exceptions.BadRecord;

/**
 *
 * @author Justin Swanson
 */
class ConditionBase extends SubRecord {

    Operator operator = Operator.EqualTo;
    LFlags flags = new LFlags(1);
    byte[] fluff = new byte[3];
    FormID comparisonValueForm = new FormID();
    float comparisonValueFloat = 0;
    byte[] padding = new byte[2];
    ConditionOption option;

    ConditionBase() {
	super();
    }

    @Override
    void export(ModExporter out) throws IOException {
	super.export(out);
	//Flags and Operator
	int operatorInt = operator.ordinal();
	operatorInt *= 32;
	LFlags tmp = new LFlags(Ln.toByteArray(operatorInt, 1));
	for (int i = 0; i < 5; i++) {
	    tmp.set(i, flags.get(i));
	}
	out.write(tmp.export(), 1);
	out.write(fluff, 3);

	//Value
	if (get(Condition.CondFlag.UseGlobal)) {
	    // This FormID is flipped, so it's an odd export.
	    out.write(comparisonValueForm.get());
	} else {
	    out.write(comparisonValueFloat);
	}

	//Function
	out.write(option.index, 2);
	out.write(padding, 2);

	option.export(out);
    }

    @Override
    void parseData(LImport in, Mod srcMod) throws BadRecord, DataFormatException, BadParameter {
	super.parseData(in, srcMod);
	//Flags and Operator
	flags.set(in.extract(1));
	int operatorInt = flags.export()[0];
	operatorInt = operatorInt & 0xE0; // Mask unrelated bits
	operatorInt /= 32; // Shift bits left 5
	operator = Condition.Operator.values()[operatorInt];
	fluff = in.extract(3);

	//Value
	if (get(Condition.CondFlag.UseGlobal)) {
	    // Use public set here, because for some reason, this FormID is flipped
	    comparisonValueForm = new FormID();
	    comparisonValueForm.set(in.extract(4));
	} else {
	    comparisonValueFloat = in.extractFloat();
	}

	//Function
	option = ConditionOption.getOption(in.extractInt(2));
	padding = in.extract(2);

	if (SPGlobal.logging()) {
	    logSync("", "New Condition.  Function: " + option.script.toString() + ", index: " + option.index);
	    logSync("", "  Operator: " + operator + ", flags: " + flags + " useGlobal: " + get(Condition.CondFlag.UseGlobal));
	    logSync("", "  Comparison Val: " + comparisonValueForm + "|" + comparisonValueFloat);
	}

	option.parseData(in, srcMod);

    }

    @Override
    ArrayList<FormID> allFormIDs() {
	ArrayList<FormID> out = new ArrayList<>(5);
	if (comparisonValueForm != null) {
	    out.add(comparisonValueForm);
	}
	out.addAll(option.allFormIDs());
	return out;
    }

    @Override
    SubRecord getNew(String type) {
	return new ConditionBase();
    }

    @Override
    boolean isValid() {
	return true;
    }

    @Override
    int getContentLength(ModExporter out) {
	return 32;
    }

    public boolean get(Condition.CondFlag flag) {
	return flags.get(flag.value);
    }

    public void set(Condition.CondFlag flag, boolean on) {
	flags.set(flag.value, on);
    }

    @Override
    ArrayList<String> getTypes() {
	return Record.getTypeList("CTDA");
    }
}
