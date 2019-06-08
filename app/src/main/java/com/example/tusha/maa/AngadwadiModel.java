package com.example.tusha.maa;

/**
 * Created by User on 12-10-2017.
 */

public class AngadwadiModel
{


    public String pre_sno;
    public String aadhar_number;
    public String mother_name;
    public String contact_number;

    // Constructor.
    public AngadwadiModel(String pre_sno,String aadhar_number, String mother_name,String contact_number) {
        this.pre_sno = pre_sno;
        this.aadhar_number = aadhar_number;
        this.mother_name = mother_name;
        this.contact_number = contact_number;
    }

    public String getAadhar_number() {
        return aadhar_number;
    }

    public String getMother_name() {
        return mother_name;
    }

    public String getContact_number() {
        return contact_number;
    }

}

