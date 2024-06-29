import { CourseResponse } from "../type";
import { useState } from "react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { addCourse } from "../api/coursesapi";
import Snackbar from "@mui/material/Snackbar";
import CourseDialogContent from "./CourseDialogContent";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogTitle from "@mui/material/DialogTitle";
import Button from "@mui/material/Button";
import { DialogContent } from "@mui/material";

function AddCourse() {
  const [open, setOpen] = useState(false);
  const [SnackBarOpen, setSnackBarOpen] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState("");
  const [course, setCourse] = useState<CourseResponse>({
    id:0, 
    code: "",
    title: "",
    season: "",
    year: "",
    status: "",
  });

  // open the modal form
  const handleClickOpen = () => {
    setOpen(true);
  };

  // Close the modal form
  const handleClose = () => {
    setOpen(false);
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCourse({ ...course, [event.target.name]: event.target.value });
  };

  const queryClient = useQueryClient();
  // Add inside the AddCourse component function
  const { mutate } = useMutation(addCourse, {
    onSuccess: () => {
      setSnackBarOpen(true);
      queryClient.invalidateQueries(["courses"]);
    },
    onError: (err) => {
      console.error(err);
    },
  });

  const handleSave = () => {
    setSnackBarMessage(`Course ${course.title} has been added successfully`);
    mutate(course);
    setCourse({
      id:0,
      code: "",
      title: "",
      year: "",
      status: "",
      season: "",
    });
    handleClose();
  };

  return (
    <>
      <Button onClick={handleClickOpen}>New Course</Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>New Course</DialogTitle>
        <DialogContent>
          <CourseDialogContent course={course} handleChange={handleChange} />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSave}>Save</Button>
        </DialogActions>
      </Dialog>
      <Snackbar
        open={SnackBarOpen}
        autoHideDuration={5000}
        onClose={() => setSnackBarOpen(false)}
        message={snackBarMessage}
      ></Snackbar>
    </>
  );
}
export default AddCourse;
