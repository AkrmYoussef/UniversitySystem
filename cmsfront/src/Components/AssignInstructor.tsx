import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import { useState } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { getallinstructors } from "../api/instructorapi";
import Snackbar from "@mui/material/Snackbar";
import Button from "@mui/material/Button";
import AssignInstructorContent from "./AssignInstructorContent";

import { addCourseToInstructor } from "../api/instructorapi";

interface AssignInstructorProps {
  open: boolean;
  handleClose: () => void;
  courseId: number;
}

function AssignInstructor({
  open,
  handleClose,
  courseId,
}: AssignInstructorProps) {
  const [SnackBarOpen, setSnackBarOpen] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState("");
  const [selectedInstructor, setSelectedInstructor] = useState(-1);

  const { data, isLoading, isError } = useQuery({
    queryKey: ["instructors"],
    queryFn: getallinstructors,
  });

  const queryClient = useQueryClient();

  const { mutate } = useMutation(addCourseToInstructor, {
    onSuccess: () => {
      queryClient.invalidateQueries(["courses"]);
      setSelectedInstructor(-1);

      handleClose();
    },
    onError: (error) => {
      console.error(error);
    },
  });

  const handleSave = () => {
    if (selectedInstructor != -1) {
      mutate({ instructorId: selectedInstructor, courseId: courseId });
    } else {
      setSnackBarMessage("Please select an instructor");
      setSnackBarOpen(true);
    }
  };
  
  if (isLoading) {
    return <DialogContent>Loading...</DialogContent>;
  }

  if (isError) {
    return <DialogContent>Error loading instructors</DialogContent>;
  }

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>Assign Instructor</DialogTitle>
      <br></br>
      <DialogContent>
        <AssignInstructorContent
          selectedInstructor={selectedInstructor}
          setSelectedInstructor={setSelectedInstructor}
          data={data}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} color="primary">
          Cancel
        </Button>
        <Button onClick={handleSave} color="primary">
          Save
        </Button>
      </DialogActions>
      <Snackbar
        open={SnackBarOpen}
        autoHideDuration={5000}
        onClose={() => setSnackBarOpen(false)}
        message={snackBarMessage}
      ></Snackbar>
    </Dialog>
  );
}

export default AssignInstructor;
