using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Video;

public class TVBehaviour : MonoBehaviour
{
    
    // Start is called before the first frame update
    void Start()
    {
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void turnTVOn()
    {
        GetComponent<VideoPlayer>().Play(); 
    }

    public void turnTVOff()
    {
        GetComponent<VideoPlayer>().Stop();
    }
}
