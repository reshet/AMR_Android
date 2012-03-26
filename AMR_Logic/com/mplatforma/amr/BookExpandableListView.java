package com.mplatforma.amr; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

public class BookExpandableListView extends Activity {
  
  // �������� �������� (�����)
  String[] groups = new String[] {"HTC", "Samsung", "LG"};
  
  // �������� ��������� (���������)
  String[] phonesHTC = new String[] {"Sensation", "Desire", "Wildfire", "Hero"};
  String[] phonesSams = new String[] {"Galaxy S II", "Galaxy Nexus", "Wave"};
  String[] phonesLG = new String[] {"Optimus", "Optimus Link", "Optimus Black", "Optimus One"};
  
  // ��������� ��� �����
  ArrayList<Map<String, String>> groupData;
  
  // ��������� ��� ��������� ����� ������
  ArrayList<Map<String, String>> childDataItem;

  // ����� ��������� ��� ��������� ���������
  ArrayList<ArrayList<Map<String, String>>> childData;
  // � ����� ��������� childData = ArrayList<childDataItem>
  
  // ������ ���������� ������ ��� ��������
  Map<String, String> m;

  ExpandableListView elvMain;
  
  
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_amr);
        
        // ��������� ��������� ����� �� ������� � ���������� �����
        groupData = new ArrayList<Map<String, String>>();
        for (String group : groups) {
          // ��������� ������ ���������� ��� ������ ������
          m = new HashMap<String, String>();
            m.put("groupName", group); // ��� ��������
            groupData.add(m);  
        }
        
        // ������ ���������� ����� ��� ������
        String groupFrom[] = new String[] {"groupName"};
        // ������ ID view-���������, � ������� ����� �������� ��������� �����
        int groupTo[] = new int[] {android.R.id.text1};
        

        // ������� ��������� ��� ��������� ��������� 
        childData = new ArrayList<ArrayList<Map<String, String>>>(); 
        
        // ������� ��������� ��������� ��� ������ ������
        childDataItem = new ArrayList<Map<String, String>>();
        // ��������� ������ ���������� ��� ������� ��������
        for (String phone : phonesHTC) {
          m = new HashMap<String, String>();
            m.put("phoneName", phone); // �������� ��������
            childDataItem.add(m);  
        }
        // ��������� � ��������� ���������
        childData.add(childDataItem);

        // ������� ��������� ��������� ��� ������ ������        
        childDataItem = new ArrayList<Map<String, String>>();
        for (String phone : phonesSams) {
          m = new HashMap<String, String>();
            m.put("phoneName", phone);
            childDataItem.add(m);  
        }
        childData.add(childDataItem);

        // ������� ��������� ��������� ��� ������� ������        
        childDataItem = new ArrayList<Map<String, String>>();
        for (String phone : phonesLG) {
          m = new HashMap<String, String>();
            m.put("phoneName", phone);
            childDataItem.add(m);  
        }
        childData.add(childDataItem);

        // ������ ���������� ��������� ��� ������
        String childFrom[] = new String[] {"phoneName"};
        // ������ ID view-���������, � ������� ����� �������� ��������� ���������
        int childTo[] = new int[] {android.R.id.text1};
        
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
            this,
            groupData,
            android.R.layout.simple_expandable_list_item_1,
            groupFrom,
            groupTo,
            childData,
            android.R.layout.simple_list_item_1,
            childFrom,
            childTo);
            
        elvMain = (ExpandableListView) findViewById(R.id.elvMain);
        elvMain.setAdapter(adapter);
    }
}