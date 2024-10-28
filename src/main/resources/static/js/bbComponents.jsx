/**
 * Checks and resumes the AudioContext if it is in a suspended state.
 * This function is specifically used with the p5.js library.
 */
function startAudioContext() {
  if (p5.soundOut.audioContext.state === "suspended") {
    p5.soundOut.audioContext.resume();
  }
}


/**
 * Returns the WebSocket URL based on the current host environment.
 * For local development, it uses 'ws://host/bbService', while for Heroku, it uses 'wss://host/bbService'.
 * @returns {string} The WebSocket URL.
 */
function BBServiceURL() {
  const host = window.location.host;
  let url = "ws://" + host + "/bbService";
  // Uncomment the line below for secure WebSocket connections on Heroku
  // if (host.toString().startsWith("localhost")) {
  //   url = "wss://" + host + "/bbService";
  // }
  return url;
}

/**
 * Represents a WebSocket channel for communication with the 'bbService'.
 * @param {string} URL - The WebSocket URL.
 * @param {function} callback - Callback function to handle received messages.
 */
class WSBBChannel {
  constructor(URL, callback) {
    this.URL = URL;
    this.wsocket = new WebSocket(URL);
    this.wsocket.onopen = (evt) => this.onOpen(evt);
    this.wsocket.onmessage = (evt) => this.onMessage(evt);
    this.wsocket.onerror = (evt) => this.onError(evt);
    this.receivef = callback;
  }

  onOpen(evt) {
    console.log("WebSocket connection opened:", evt);
  }

  onMessage(evt) {
    console.log("WebSocket message received:", evt);
    // Ignore the first 'Connection established.' message
    if (evt.data !== "Connection established.") {
      this.receivef(evt.data);
    }
  }

  onError(evt) {
    console.error("WebSocket error:", evt);
  }

  /**
   * Sends a message to the WebSocket server.
   * @param {number} x - X-coordinate.
   * @param {number} y - Y-coordinate.
   */
  send(x, y) {
    let msg = `{ "x": ${x}, "y": ${y} }`;
    console.log("Sending message:", msg);
    this.wsocket.send(msg);
  }

  /**
   * Closes the WebSocket connection.
   */
  close() {
    this.wsocket.close();
    console.log("WebSocket connection closed.");
  }
}


/**
 * React function component that renders a canvas for drawing and interacts with WebSocket for real-time updates.
 */
function BBCanvas() {
  const [svrStatus, setSvrStatus] = React.useState({ loadingState: "Loading Canvas..." });
  const comunicationWS = React.useRef(null);
  const myp5 = React.useRef(null);

  const sketch = function (p) {
    p.setup = function () {
      p.createCanvas(700, 410);
    };
    p.draw = function () {
      if (p.mouseIsPressed === true) {
        p.fill(0, 0, 0);
        p.ellipse(p.mouseX, p.mouseY, 20, 20);
        comunicationWS.current.send(p.mouseX, p.mouseY);
      }
      if (p.mouseIsPressed === false) {
        p.fill(255, 255, 255);
      }
    };
  };

  React.useEffect(() => {
    myp5.current = new p5(sketch, "container");
    setSvrStatus({ loadingState: "Canvas Loaded" });

    comunicationWS.current = new WSBBChannel(BBServiceURL(), (msg) => {
      var obj = JSON.parse(msg);
      console.log("Received message:", msg);
      drawPoint(obj.x, obj.y);
    });

    return () => {
      console.log("Closing WebSocket connection...");
      comunicationWS.current.close();
    };
  }, []);

  function drawPoint(x, y) {
    myp5.current.ellipse(x, y, 20, 20);
  }

  return (
    <div>
      <h4>Drawing status: {svrStatus.loadingState}</h4>
    </div>
  );
}


/**
 * React function component that serves as the main editor interface.
 * @param {string} name - The name of the user.
 */
function Editor({ name }) {
  return (
    <div>
      <h1>Hello, {name}</h1>
      <hr />
      <div id="toolstatus"></div>
      <hr />
      <div id="container">
        <BBCanvas />
      </div>
      <hr />
      <div id="info"></div>
    </div>
  );
}


const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<Editor name="Johann Amaya" />);
